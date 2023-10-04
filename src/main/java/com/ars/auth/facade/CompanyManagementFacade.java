package com.ars.auth.facade;

import com.ars.auth.domain.entity.Company;
import com.ars.auth.domain.entity.State;
import com.ars.auth.model.CompanyDto;
import com.ars.auth.model.CreateCompanyRequest;
import com.ars.auth.service.CompanyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyManagementFacade {

  CompanyService companyService;

  ModelMapper modelMapper;

  public CompanyDto get(Integer id) {
    return modelMapper.map(companyService.findById(id), CompanyDto.class);
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

  public List<CompanyDto> getAll() {
    return companyService.findAll().stream()
        .map(company -> modelMapper.map(company, CompanyDto.class))
        .toList();
  }

  @Transactional
  public void updateCompany(CompanyDto companyDto) {
    Company company = companyService.findById(companyDto.getId());
    modelMapper.map(companyDto, company);
    companyService.save(company);
  }


}
