package com.ars.auth.domain.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class GeneralEntityListener {

  @PrePersist
  @PreUpdate
  private void perPersist(AbstractEntity entity) {
//    if (entity.getCreatedBy() == null) {
//      entity.setCreatedBy("system");
//    }
//    if (entity.getCreatedDatetime() == null) {
//      entity.setCreatedDatetime(ZonedDateTime.now());
//    }
//    entity.setUpdatedBy("system");
//    entity.setUpdatedDatetime(ZonedDateTime.now());
  }
}
