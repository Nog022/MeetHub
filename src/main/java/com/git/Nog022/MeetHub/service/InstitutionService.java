package com.git.Nog022.MeetHub.service;


import com.git.Nog022.MeetHub.dto.InstitutionDTO;
import com.git.Nog022.MeetHub.dto.InstitutionResponseDTO;
import com.git.Nog022.MeetHub.dto.UserDTO;
import com.git.Nog022.MeetHub.entity.Institution;
import com.git.Nog022.MeetHub.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface InstitutionService {

    ResponseEntity<?> save(InstitutionDTO institutionDTO);

    ResponseEntity<?> update(InstitutionDTO institutionDTO);

    void joinInstitutionIdWithDomain(User user);

    List<UserDTO> findAllUsers(String cnpj);

    void joinInstitutionWithDomain(String email);

    Institution findById(Long id);

}
