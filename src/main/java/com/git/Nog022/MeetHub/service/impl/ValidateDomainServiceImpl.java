package com.git.Nog022.MeetHub.service.impl;


import com.git.Nog022.MeetHub.entity.Institution;
import com.git.Nog022.MeetHub.repository.InstitutionRepository;
import com.git.Nog022.MeetHub.service.ValidateDomainService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ValidateDomainServiceImpl implements ValidateDomainService {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Override
    public Optional<Institution> validateDomain(String email) {

        String domain = email.substring(email.indexOf("@") + 1);

        Optional<Institution> optionalInstitution = institutionRepository.findByDomains(domain);
        return optionalInstitution;

    }
}
