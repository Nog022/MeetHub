package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.entity.Company;

import java.util.Optional;

public interface ValidateDomainService {

    Optional<Company> validateDomain(String domain);

}
