package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.config.TokenService;
import com.git.Nog022.MeetHub.dto.*;
import com.git.Nog022.MeetHub.entity.Institution;
import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.enums.UserRole;
import com.git.Nog022.MeetHub.exception.ValidationException;

import com.git.Nog022.MeetHub.repository.InstitutionRepository;

import com.git.Nog022.MeetHub.service.InstitutionService;
import com.git.Nog022.MeetHub.service.UserService;
import com.git.Nog022.MeetHub.service.ValidateDomainService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InstitutionServiceImpl implements InstitutionService {

    public static Logger logger = LoggerFactory.getLogger(InstitutionServiceImpl.class);


    @Autowired
    private InstitutionRepository institutionRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private ValidateDomainService validateDomainService;
    @Autowired
    private TokenService tokenService;

    @Override
    public InstitutionResponseDTO save(InstitutionDTO institutionDTO) {
        logger.info("entrou em salvar institution");
        User user = userService.findByEmail(institutionDTO.userEmail());


        if(user != null && institutionRepository.findByCnpj(institutionDTO.cnpj()) == null) {
            logger.info("entrou no if");

            Institution institution = new Institution();

            institution.setCnpj(institutionDTO.cnpj());
            institution.setName(institutionDTO.name());
            institution.setLocals(null);
            institution.setUsers(new ArrayList<>());
            institution.getUsers().add(setUserDTO(user, institution));
            institution.setDomains(institutionDTO.domain() == null ? Collections.emptyList() : new ArrayList<>(institutionDTO.domain()));

            Institution savedInstitution = institutionRepository.save(institution);
            userService.save(user);
            return toResponseDTO(savedInstitution, user);

        }

        throw new ValidationException("Erro Save Institution");
    }

    @Override
    public InstitutionDTO update(InstitutionDTO institutionDTO) {
        logger.info("entrou em atualizar institution");
        try{
            Institution institution = institutionRepository.findByCnpj(institutionDTO.cnpj());
            institution.setName(institutionDTO.name());
            institution.getDomains().clear();
            institution.getDomains().addAll(institutionDTO.domain() == null ? Collections.emptyList() : new ArrayList<>(institutionDTO.domain()));
            institutionRepository.save(institution);


            return new InstitutionDTO(
                    institution.getId(),
                    institution.getCnpj(),
                    institution.getName(),
                    institution.getDomains(),
                    null

            );
        } catch (Exception e) {
            logger.error("Erro atualizar institution", e);
            throw new RuntimeException(e);
        }


    }


    @Override
    public void joinInstitutionIdWithDomain(User user) {
        try {
            Optional<Institution> optionalInstitution = validateDomainService.validateDomain(user.getEmail());

            if (optionalInstitution.isPresent()) {
                logger.info("Empresa encontrada");
                Institution institution = optionalInstitution.get();
                institution.getUsers().add(user);
                user.setInstitution(institution);
                userService.save(user);
                institutionRepository.save(institution);

            }else{
                logger.info("Not found Institution with email " + user.getEmail());
            }

        } catch (Exception e) {
            logger.error("joinInstitution erro " + e.getMessage());
            throw new RuntimeException("Erro while processing join Institution") ;
        }
    }

    //TODO terminar a parte do refresh
    @Override
    public void joinInstitutionWithDomain(String email) {
        try {
            logger.info("joinInstitutionWithDomain()");
            User user = userService.findByEmail(email);

            logger.info("User: " + user);
            Optional<Institution> optionalInstitution = validateDomainService.validateDomain(email);
            logger.info("Institution encontrada: "  + optionalInstitution);

            if (optionalInstitution.isPresent() && user.getInstitution() != null) {
                logger.info("Empresa encontrada");
                Institution institution = optionalInstitution.get();
                institution.getUsers().add(user);
                user.setInstitution(institution);
                userService.save(user);
                institutionRepository.save(institution);

            }else{
                logger.info("The email " + user.getEmail() + " is not valid");
            }

        } catch (Exception e) {
            logger.error("joinInstitution erro " + e.getMessage());
            throw new RuntimeException("Erro while processing join Institution") ;
        }
    }

    @Override
    public Institution findById(Long id) {
        logger.info("entrou em findById");
        return institutionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Local not found"));


    }


    @Override
    public List<UserDTO> findAllUsers(String cnpj) {
        Institution institution = institutionRepository.findByCnpj(cnpj);
        List<User> users = institution.getUsers();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO dto = new UserDTO(
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getCpf(),
                    user.getRole()

            );
            userDTOs.add(dto);
        }

        return userDTOs;
    }





    private User setUserDTO(User user, Institution institution) {
        logger.info("entrou em setUserDTO");
        user.setInstitution(institution);
        user.setRole(UserRole.ADMIN);
        return user;
    }

    private InstitutionResponseDTO toResponseDTO(Institution institution, User user) {

        return new InstitutionResponseDTO(
                institution.getId(),
                institution.getCnpj(),
                institution.getName(),
                institution.getDomains(),
                tokenService.generateToken(user)
        );
    }









}
