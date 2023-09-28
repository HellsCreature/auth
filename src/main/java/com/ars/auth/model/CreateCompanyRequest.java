package com.ars.auth.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyRequest {

  @NotBlank
  String bin;

  @NotBlank
  String name;
}
