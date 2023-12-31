package com.ars.auth.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

//Arseniy: Вытаскивает имя пользователя из токена, чтобы записать его в поля updatedBy и createdBy сущностей
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.empty();
    }

    Object obj = authentication.getPrincipal();

    if (obj instanceof Jwt jwt) {
      return Optional.of(jwt.getClaimAsString("preferred_username"));
    }

    return Optional.empty();
  }
}