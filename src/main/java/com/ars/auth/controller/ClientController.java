package com.ars.auth.controller;

import com.ars.auth.facade.ClientManagementFacade;
import com.ars.auth.model.ClientDto;
import com.ars.auth.model.CreateClientRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@PreAuthorize("hasRole('admin')")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientController {

  ClientManagementFacade clientManagementFacade;

  @GetMapping
  public List<ClientDto> getAll() {
    return clientManagementFacade.getAll();
  }

  @GetMapping("/{id}")
  public ClientDto getUser(@PathVariable Integer id) {
    return clientManagementFacade.getClient(id);
  }

  @PutMapping
  public void updateUser(@Valid @RequestBody ClientDto userDto) {
    clientManagementFacade.updateClient(userDto);
  }

  @PostMapping
  public ClientDto createUser(@Valid @RequestBody CreateClientRequest request) {
    return clientManagementFacade.createClient(request);
  }

  @PostMapping("/{id}/reset-secret")
  public ClientDto resetSecret(@PathVariable Integer id) {
    return clientManagementFacade.resetSecret(id);
  }

  @PostMapping("/{id}/deactivate")
  public void deactivate(@PathVariable Integer id) {
    clientManagementFacade.deactivate(id);
  }

  @PostMapping("/{id}/activate")
  public void activate(@PathVariable Integer id) {
    clientManagementFacade.activate(id);
  }

  @PostMapping("/{id}/suspend")
  public void suspend(@PathVariable Integer id) {
    clientManagementFacade.suspend(id);
  }

  @GetMapping("{id}/roles")
  public List<String> getRoles(@PathVariable Integer id) {
    return clientManagementFacade.getRoles(id);
  }

  @PostMapping("/{id}/roles")
  public void addRoles(@PathVariable Integer id, @RequestBody List<String> roles) {
    clientManagementFacade.addRoles(id, roles);
  }

  @DeleteMapping("{id}/roles")
  public void removeRoles(@PathVariable Integer id, @RequestBody List<String> roles) {
    clientManagementFacade.removeRoles(id, roles);
  }

}
