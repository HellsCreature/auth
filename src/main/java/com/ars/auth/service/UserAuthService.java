package com.ars.auth.service;

import java.util.List;
import org.keycloak.representations.AccessTokenResponse;

//Arseniy: интерфейс для сервиса аутентификации пользователей
public interface UserAuthService {

  String createUser(String username, String password, String email, String firstname,
      String lastname, List<String> roles, Integer companyId);

  AccessTokenResponse getToken(String username, String password);

  void addRoles(String userId, List<String> roles);

  void removeRoles(String userId, List<String> roles);

  List<String> getRoles(String userId);

  void resetPassword(String userId, String password);
}
