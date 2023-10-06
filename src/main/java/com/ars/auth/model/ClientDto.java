package com.ars.auth.model;

import com.ars.auth.domain.entity.State;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ClientDto {

  Integer id;

  Integer companyId;

  String clientId;

  @JsonInclude(Include.NON_NULL)
  String clientSecret;

  State state;
}
