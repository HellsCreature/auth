package com.ars.auth.facade;

import com.ars.auth.domain.entity.State;
import com.ars.auth.domain.entity.UserAccount;
import com.ars.auth.domain.entity.UserAccountType;
import com.ars.auth.model.ClientDto;
import com.ars.auth.model.CreateClientRequest;
import com.ars.auth.service.KeycloakService;
import com.ars.auth.service.UserAccountService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientManagementFacade {
  UserAccountService userAccountService;
  KeycloakService keycloakService;
  ModelMapper modelMapper;

  static Integer LENGTH = 20;

  public ClientDto getClient(Integer id) {
    UserAccount userAccount = userAccountService.findById(id);
    return modelMapper.map(userAccount, ClientDto.class);
  }

  @Transactional
  public ClientDto createClient(CreateClientRequest request) {

    //todo проверить, есть ли уже такой юзер

    String clientId = RandomStringUtils.randomAlphanumeric(LENGTH);
    String clientSecret = RandomStringUtils.randomAlphanumeric(LENGTH);
    String email = "thisIsFakeEmailForClient_" + clientId + "@qasdx.sdq";

    String keycloakUserId = keycloakService.createUser(clientId, clientSecret,
        email, null, null, List.of("client"), request.getCompanyId());

    UserAccount userAccount = userAccountService.create(request.getCompanyId(), keycloakUserId, clientId,
        email, UserAccountType.CLIENT);

    ClientDto clientDto = modelMapper.map(userAccount, ClientDto.class);
    clientDto.setClientSecret(clientSecret);

    return clientDto;
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

  public List<ClientDto> getAll() {
    return userAccountService.findAllClients().stream()
        .map(userAccount -> modelMapper.map(userAccount, ClientDto.class))
        .toList();
  }

  @Transactional
  public void updateClient(ClientDto clientDto) {
    UserAccount userAccount = userAccountService.findById(clientDto.getId());
    modelMapper.map(clientDto, userAccount);
    userAccountService.save(userAccount);
  }

  public List<String> getRoles(Integer id) {
    UserAccount userAccount = userAccountService.findById(id);
    return keycloakService.getRoles(userAccount.getExternalId());
  }

  public ClientDto resetSecret(Integer id) {
    UserAccount userAccount = userAccountService.findById(id);
    String clientSecret = RandomStringUtils.randomAlphanumeric(LENGTH);
    keycloakService.resetPassword(userAccount.getExternalId(), clientSecret);

    ClientDto clientDto = modelMapper.map(userAccount, ClientDto.class);
    clientDto.setClientSecret(clientSecret);

    return clientDto;
  }
}
