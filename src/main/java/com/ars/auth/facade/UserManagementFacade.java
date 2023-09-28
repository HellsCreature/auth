package com.ars.auth.facade;

import com.ars.auth.domain.entity.State;
import com.ars.auth.model.LoginRequest;
import com.ars.auth.service.KeycloakService;
import com.ars.auth.domain.entity.UserAccount;
import com.ars.auth.model.CreateUserRequest;
import com.ars.auth.service.UserAccountService;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserManagementFacade {

  UserAccountService userAccountService;
  KeycloakService keycloakService;

  @Transactional
  public Response createUser(CreateUserRequest request) {
   String keycloakUserId = keycloakService.createUser(request.getUsername(), request.getPassword(),
        request.getEmail(), request.getFirstname(), request.getLastname());

    userAccountService.create(request.getCompanyId(), keycloakUserId, request.getUsername(),
        request.getEmail());

//    return response;
    return null;
  }

  @Transactional
  public void deactivate(Integer id) {
    UserAccount userAccount = userAccountService.findById(id);
    userAccount.setState(State.INACTIVE);
    userAccountService.save(userAccount);
  }

  public AccessTokenResponse login(LoginRequest loginRequest) {
    return keycloakService.getToken(loginRequest.getUsername(), loginRequest.getPassword());
  }

}
