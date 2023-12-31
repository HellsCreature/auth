package com.ars.auth.facade;

import com.ars.auth.domain.entity.State;
import com.ars.auth.domain.entity.UserAccount;
import com.ars.auth.domain.entity.UserAccountType;
import com.ars.auth.model.CreateUserRequest;
import com.ars.auth.model.LoginRequest;
import com.ars.auth.model.UserDto;
import com.ars.auth.service.UserAccountService;
import com.ars.auth.service.UserAuthService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.keycloak.representations.AccessTokenResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//Arseniy: Фасад для управления пользователями
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserManagementFacade {

  UserAccountService userAccountService;
  UserAuthService userAuthService;
  ModelMapper modelMapper;

  public AccessTokenResponse login(LoginRequest loginRequest) {
    return userAuthService.getToken(loginRequest.getUsername(), loginRequest.getPassword());
  }

  public UserDto getUser(Integer id) {
    UserAccount userAccount = userAccountService.findById(id);
    return modelMapper.map(userAccount, UserDto.class);
  }

  @Transactional
  public void createUser(CreateUserRequest request) {

    //todo проверить, есть ли уже такой юзер

   String keycloakUserId = userAuthService.createUser(request.getUsername(), request.getPassword(),
        request.getEmail(), request.getFirstname(), request.getLastname(), request.getRoles(), request.getCompanyId());

    userAccountService.create(request.getCompanyId(), keycloakUserId, request.getUsername(),
        request.getEmail(), UserAccountType.USER);
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
    userAuthService.addRoles(userAccount.getExternalId(), roles);
  }

  public void removeRoles(Integer id, List<String> roles) {
    UserAccount userAccount = userAccountService.findById(id);
    userAuthService.removeRoles(userAccount.getExternalId(), roles);
  }

  public List<UserDto> getAll() {
    return userAccountService.findAllUsers().stream()
        .map(userAccount -> modelMapper.map(userAccount, UserDto.class))
        .toList();
  }

  @Transactional
  public void updateUser(UserDto userDto) {
    UserAccount userAccount = userAccountService.findById(userDto.getId());
    modelMapper.map(userDto, userAccount);
    userAccountService.save(userAccount);
  }

  public List<String> getRoles(Integer id) {
    UserAccount userAccount = userAccountService.findById(id);
    return userAuthService.getRoles(userAccount.getExternalId());
  }
}
