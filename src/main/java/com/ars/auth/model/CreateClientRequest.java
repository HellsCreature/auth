package com.ars.auth.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateClientRequest {

  @NotNull
  Integer companyId;
}

