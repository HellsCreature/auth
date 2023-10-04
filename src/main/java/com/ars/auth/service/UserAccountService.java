package com.ars.auth.service;

import com.ars.auth.domain.entity.State;
import com.ars.auth.domain.entity.UserAccount;
import com.ars.auth.domain.entity.UserAccountType;
import com.ars.auth.domain.repository.UserAccountRepository;
import com.ars.auth.model.UserDto;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserAccountService {

  UserAccountRepository userAccountRepository;

  public UserAccount create(Integer companyId, String externalId, String username, String email, UserAccountType type) {
    UserAccount userAccount = UserAccount.builder()
        .username(username)
        .email(email)
        .companyId(companyId)
        .externalId(externalId)
        .state(State.ACTIVE)
        .type(type)
        .build();

    return userAccountRepository.save(userAccount);
  }

  public UserAccount findById(Integer id) {
    return userAccountRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  public UserAccount save(UserAccount userAccount) {
    return userAccountRepository.save(userAccount);
  }

  public List<UserAccount> findAll() {
    return userAccountRepository.findAll();
  }
}
