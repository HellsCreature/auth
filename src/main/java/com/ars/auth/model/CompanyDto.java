package com.ars.auth.model;

import com.ars.auth.domain.entity.State;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CompanyDto {

  String bin;

  String name;

  State state;
}