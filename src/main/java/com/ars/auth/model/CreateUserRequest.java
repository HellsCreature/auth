package com.ars.auth.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

  @NotNull
  Integer companyId;

  @NotBlank
  String username;

  @NotBlank
  String password;

  @Email
  @NotBlank
  String email;

  String firstname;

  String lastname;

  @NotNull
  List<String> roles;
}

