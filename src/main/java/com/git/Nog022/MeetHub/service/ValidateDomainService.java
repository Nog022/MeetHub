package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.entity.Institution;

import java.util.Optional;

public interface ValidateDomainService {

    Optional<Institution> validateDomain(String domain);

}
