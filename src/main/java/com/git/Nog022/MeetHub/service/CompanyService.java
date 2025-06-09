package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.dto.CompanyDTO;
import com.git.Nog022.MeetHub.dto.JoinCompanyDTO;
import com.git.Nog022.MeetHub.dto.UserDTO;
import com.git.Nog022.MeetHub.entity.Company;
import com.git.Nog022.MeetHub.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface CompanyService {

    CompanyDTO save(CompanyDTO companyDTO);

    //String joinCompanyWithDomain(JoinCompanyDTO dto);

    void joinCompanyWithDomain(User user);

    List<UserDTO> findAllUsers(String cnpj);

    void joinCompanyWithDomain(String email);

}
