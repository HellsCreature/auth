package com.ars.auth.facade;

import com.ars.auth.domain.entity.State;
import com.ars.auth.model.LoginRequest;
import com.ars.auth.model.UserDto;
import com.ars.auth.service.KeycloakService;
import com.ars.auth.domain.entity.UserAccount;
import com.ars.auth.model.CreateUserRequest;
import com.ars.auth.service.UserAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.core.Response;
import java.util.List;
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

  ObjectMapper objectMapper;

  public AccessTokenResponse login(LoginRequest loginRequest) {
    return keycloakService.getToken(loginRequest.getUsername(), loginRequest.getPassword());
  }

  public UserDto getUser(Integer id) {
    UserAccount userAccount = userAccountService.findById(id);
    return objectMapper.convertValue(userAccount, UserDto.class);
  }

  @Transactional
  public void createUser(CreateUserRequest request) {
   String keycloakUserId = keycloakService.createUser(request.getUsername(), request.getPassword(),
        request.getEmail(), request.getFirstname(), request.getLastname(), request.getRoles());

    userAccountService.create(request.getCompanyId(), keycloakUserId, request.getUsername(),
        request.getEmail());
  }

  @Transactional
  public void deactivate(Integer id) {
    UserAccount userAccount = userAccountService.findById(id);
    userAccount.setState(State.INACTIVE);
    userAccountService.save(userAccount);
  }

  @Transactional
  public void activate(Integer id) {
    UserAccount userAccount = userAccountService.findById(id);
    userAccount.setState(State.ACTIVE);
    userAccountService.save(userAccount);
  }

  @Transactional
  public void suspend(Integer id) {
    UserAccount userAccount = userAccountService.findById(id);
    userAccount.setState(State.SUSPENDED);
    userAccountService.save(userAccount);
  }

  public void addRoles(Integer id, List<String> roles) {
    UserAccount userAccount = userAccountService.findById(id);
    keycloakService.addRoles(userAccount.getExternalId(), roles);
  }

  public void removeRoles(Integer id, List<String> roles) {
    UserAccount userAccount = userAccountService.findById(id);
    keycloakService.removeRoles(userAccount.getExternalId(), roles);
  }

}
