package com.ars.auth.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class JwtUtils {

  public static String getCompanyIdFromToken() {
    Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    return jwt.getClaimAsString("companyId");
  }

}
