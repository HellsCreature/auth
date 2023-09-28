package com.ars.auth.facade;

import com.ars.auth.model.CreateCompanyRequest;
import com.ars.auth.service.CompanyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyManagementFacade {

  CompanyService companyService;

  public void create(CreateCompanyRequest request) {
    companyService.create(request.getBin(), request.getName());
  }


}
