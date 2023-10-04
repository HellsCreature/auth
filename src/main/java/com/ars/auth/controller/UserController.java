package com.ars.auth.controller;

import com.ars.auth.facade.UserManagementFacade;
import com.ars.auth.model.CreateUserRequest;
import com.ars.auth.model.UserDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@PreAuthorize("hasRole('admin')")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

  UserManagementFacade userManagementFacade;

  @GetMapping
  public List<UserDto> getAll() {
    return userManagementFacade.getAll();
  }

  @GetMapping("/{id}")
  public UserDto getUser(@PathVariable Integer id) {
    return userManagementFacade.getUser(id);
  }

  @PutMapping
  public void updateUser(@Valid @RequestBody UserDto userDto) {
    userManagementFacade.updateUser(userDto);
  }

  @PostMapping
  public void createUser(@Valid @RequestBody CreateUserRequest request) {
    userManagementFacade.createUser(request);
  }

  @PostMapping("/{id}/deactivate")
  public void deactivate(@PathVariable Integer id) {
    userManagementFacade.deactivate(id);
  }

  @PostMapping("/{id}/activate")
  public void activate(@PathVariable Integer id) {
    userManagementFacade.activate(id);
  }

  @PostMapping("/{id}/suspend")
  public void suspend(@PathVariable Integer id) {
    userManagementFacade.suspend(id);
  }

  @PostMapping("/{id}/roles")
  public void addRoles(@PathVariable Integer id, @RequestBody List<String> roles) {
    userManagementFacade.addRoles(id, roles);
  }

  @DeleteMapping("{id}/roles")
  public void removeRoles(@PathVariable Integer id, @RequestBody List<String> roles) {
    userManagementFacade.removeRoles(id, roles);
  }

}
