package com.ars.auth.service;

import jakarta.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KeycloakService {

  //TODO Подумать, где сделать интерфейс, чтобы юзать кейклоак или что-то другое
  @Value("${keycloak.auth-server-url}")
  public String serverURL;
  @Value("${keycloak.realm}")
  public String realm;
  @Value("${keycloak.resource}")
  public String clientID;
  @Value("${keycloak.credentials.secret}")
  public String clientSecret;

  private Keycloak getClientCredentialsInstance() {

    return KeycloakBuilder.builder()
        .realm(realm)
        .serverUrl(serverURL)
        .clientId(clientID)
        .clientSecret(clientSecret)
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .build();
  }

  private Keycloak getUserPasswordCredentialsInstance(String username, String password) {

    //todo: проверить, что юзер есть в базе сервиса

    return KeycloakBuilder.builder()
        .realm(realm)
        .serverUrl(serverURL)
        .clientId(clientID)
        .clientSecret(clientSecret)
        .grantType(OAuth2Constants.PASSWORD)
        .username(username)
        .password(password)
        .build();
  }

  public String createUser(String username, String password, String email, String firstname,
      String lastname, List<String> roles) {

    UsersResource usersResource = getClientCredentialsInstance().realm(realm).users();
    CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);

    UserRepresentation kcUser = new UserRepresentation();
    kcUser.setUsername(username);
    kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
    kcUser.setFirstName(firstname);
    kcUser.setLastName(lastname);
    kcUser.setEmail(email);
    kcUser.setEnabled(true);
    kcUser.setEmailVerified(false);

    Response response = usersResource.create(kcUser);

    String path = null;
    try {
      path = new URL(response.getHeaderString("Location")).getPath();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }

    String userId = path.substring(path.lastIndexOf("/") + 1);

    addRoles(userId, roles);

    return userId;
  }

  private CredentialRepresentation createPasswordCredentials(String password) {
    CredentialRepresentation passwordCredentials = new CredentialRepresentation();
    passwordCredentials.setTemporary(false);
    passwordCredentials.setType(CredentialRepresentation.PASSWORD);
    passwordCredentials.setValue(password);
    return passwordCredentials;
  }

  public AccessTokenResponse getToken(String username, String password) {
    //todo убрать лишнее из токена вернуть кастомный токен
//    return getUserPasswordCredentialsInstance(username, password).tokenManager().getAccessTokenString();
    return getUserPasswordCredentialsInstance(username, password).tokenManager().getAccessToken();
  }

  public void addRoles(String userId, List<String> roles) {

    List<RoleRepresentation> roleRepresentations = getClientCredentialsInstance()
        .realm(realm)
        .users()
        .get(userId)
        .roles()
        .realmLevel()
        .listAvailable()
        .stream()
        .filter(roleRepresentation -> roles.contains(roleRepresentation.getName()))
        .toList();

    //todo: если передали несуществующую роль, то вернуть ошибку

    getClientCredentialsInstance()
        .realm(realm)
        .users()
        .get(userId)
        .roles()
        .realmLevel()
        .add(roleRepresentations);
  }

  public void removeRoles(String userId, List<String> roles) {
    List<RoleRepresentation> roleRepresentations = getClientCredentialsInstance()
        .realm(realm)
        .users()
        .get(userId)
        .roles()
        .realmLevel()
        .listAll()
        .stream()
        .filter(roleRepresentation -> roles.contains(roleRepresentation.getName()))
        .toList();

    getClientCredentialsInstance()
        .realm(realm)
        .users()
        .get(userId)
        .roles()
        .realmLevel()
        .remove(roleRepresentations);
  }

}

