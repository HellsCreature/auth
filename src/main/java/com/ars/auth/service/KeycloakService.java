package com.ars.auth.service;

import jakarta.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
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
      String lastname) {

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

    System.out.println(response.getHeaderString("Location"));

    String path = null;
    try {
      path = new URL(response.getHeaderString("Location")).getPath();
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
    String userId = path.substring(path.lastIndexOf("/") + 1);
    System.out.println(userId);

//    return response;
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
//    return getUserPasswordCredentialsInstance(username, password).tokenManager().getAccessTokenString();
    return getUserPasswordCredentialsInstance(username, password).tokenManager().getAccessToken();
  }


}

