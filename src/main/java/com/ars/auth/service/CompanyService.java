package com.ars.auth.service;

import com.ars.auth.domain.entity.Company;
import com.ars.auth.domain.entity.State;
import com.ars.auth.domain.entity.UserAccount;
import com.ars.auth.domain.repository.CompanyRepository;
import com.ars.auth.model.CompanyDto;
import com.ars.auth.model.UserDto;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyService {

  CompanyRepository companyRepository;

  public Company findById(Integer id) {
    return companyRepository.findById(id).orElseThrow(NotFoundException::new);
  }

  public Company create(String bin, String name) {
    Company company = Company.builder()
        .bin(bin)
        .name(name)
        .state(State.ACTIVE)
        .build();

    return companyRepository.save(company);
  }

  public Company save(Company company) {
    return companyRepository.save(company);
  }

  public List<Company> findAll() {
    return companyRepository.findAll();
  }
}
