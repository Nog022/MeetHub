package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.dto.CompanyDTO;
import com.git.Nog022.MeetHub.entity.Company;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public interface CompanyService {

    Company save(CompanyDTO companyDTO);


}
