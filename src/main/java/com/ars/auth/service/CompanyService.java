package com.ars.auth.service;

import com.ars.auth.domain.entity.State;
import com.ars.auth.domain.repository.CompanyRepository;
import com.ars.auth.domain.entity.Company;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyService {

  CompanyRepository companyRepository;

  public Company create (String bin, String name) {
    Company company = Company.builder()
        .bin(bin)
        .name(name)
        .state(State.ACTIVE)
        .build();

    return companyRepository.save(company);
  }

}
