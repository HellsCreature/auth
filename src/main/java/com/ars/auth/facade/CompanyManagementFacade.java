package com.ars.auth.facade;

import com.ars.auth.domain.entity.Company;
import com.ars.auth.domain.entity.State;
import com.ars.auth.domain.entity.UserAccount;
import com.ars.auth.model.CompanyDto;
import com.ars.auth.model.CreateCompanyRequest;
import com.ars.auth.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyManagementFacade {

  CompanyService companyService;

  ObjectMapper objectMapper;

  public CompanyDto get(Integer id) {
    return objectMapper.convertValue(companyService.findById(id), CompanyDto.class);
  }

  public void create(CreateCompanyRequest request) {
    companyService.create(request.getBin(), request.getName());
  }

  @Transactional
  public void deactivate(Integer id) {
    Company company = companyService.findById(id);
    company.setState(State.INACTIVE);
    companyService.save(company);
  }

  @Transactional
  public void activate(Integer id) {
    Company company = companyService.findById(id);
    company.setState(State.ACTIVE);
    companyService.save(company);
  }

  @Transactional
  public void suspend(Integer id) {
    Company company = companyService.findById(id);
    company.setState(State.SUSPENDED);
    companyService.save(company);
  }


}
