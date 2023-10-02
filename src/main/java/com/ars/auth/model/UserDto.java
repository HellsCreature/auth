package com.ars.auth.model;

import com.ars.auth.domain.entity.State;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserDto {

  Integer companyId;

  String username;

  String email;

  State state;
}
