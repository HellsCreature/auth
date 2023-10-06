package com.ars.auth.model;

import com.ars.auth.domain.entity.State;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CompanyDto {

  Integer id;

  String bin;

  String name;

  State state;
}
