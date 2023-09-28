package com.ars.auth.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Table
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserAccount extends AbstractEntity {
//todo добавить ограничения на уникальность
  Integer companyId;

  String externalId;

  String username;

  String email;

  @Enumerated(EnumType.STRING)
  State state;
}
