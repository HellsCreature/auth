package com.ars.auth.controller;

import com.ars.auth.facade.UserManagementFacade;
import com.ars.auth.model.LoginRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginController {

  UserManagementFacade userManagementFacade;

  @PostMapping("/login")
  public AccessTokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
    return userManagementFacade.login(loginRequest);
  }

}
