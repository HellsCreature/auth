package com.ars.auth.controller;

import com.ars.auth.model.CreateCompanyRequest;
import com.ars.auth.facade.CompanyManagementFacade;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyController {

  CompanyManagementFacade companyManagementFacade;

  @PostMapping("/create")
  public void createCompany(@Valid @RequestBody CreateCompanyRequest request) {
    companyManagementFacade.create(request);
  }
}
