package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.entity.Company;
import com.git.Nog022.MeetHub.repository.CompanyRepository;
import com.git.Nog022.MeetHub.service.ValidateDomainService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ValidateDomainServiceImpl implements ValidateDomainService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Optional<Company> validateDomain(String email) {

        String domain = email.substring(email.indexOf("@") + 1);

        Optional<Company> optionalCompany = companyRepository.findByDomains(domain);
        return optionalCompany;

    }
}
