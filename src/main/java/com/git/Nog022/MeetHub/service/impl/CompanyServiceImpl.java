package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.config.SecurityConfig;
import com.git.Nog022.MeetHub.dto.CompanyDTO;
import com.git.Nog022.MeetHub.dto.CompanyResponseDTO;
import com.git.Nog022.MeetHub.dto.LocalDTO;
import com.git.Nog022.MeetHub.dto.UserDTO;
import com.git.Nog022.MeetHub.entity.Company;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.exception.ValidationException;
import com.git.Nog022.MeetHub.repository.CompanyRepository;
import com.git.Nog022.MeetHub.service.CompanyService;
import com.git.Nog022.MeetHub.service.LocalService;
import com.git.Nog022.MeetHub.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    public static Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);


    @Autowired
    private CompanyRepository companyRepository;



    @Autowired
    private LocalService localService;

    @Autowired
    private UserService userService;


    @Override
    public Company save(CompanyDTO companyDTO) {
        logger.info("entrou em salvar company");


        if(verifyLocal(companyDTO) && verifyUser(companyDTO)) {
            logger.info("entrou no if");

            Company company = new Company();
            company.setName(companyDTO.name());
            company.setLocals(setLocalDTO(companyDTO, company));
            company.setUsers(setUserDTO(companyDTO, company));
            return companyRepository.save(company);

        }

        throw new ValidationException("Locals and Users must not be empty.");
    }

    private List<Local> setLocalDTO(CompanyDTO companyDTO, Company company) {

        List<Local> locals = new ArrayList<>();
        companyDTO.locals().forEach(localDTO -> {
            Local local = new Local();
            local.setName(localDTO.name());
            local.setAddress(localDTO.address());
            local.setCity(localDTO.city());
            local.setState(localDTO.state());
            local.setRoomBlockType(localDTO.roomBlockType());
            local.setRoomLocationType(localDTO.roomLocationType());
            local.setCompany(company);
            locals.add(local);
        });

        return locals;
    }

    private List<User> setUserDTO(CompanyDTO companyDTO, Company company) {

        List<User> users = new ArrayList<>();
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        companyDTO.user().forEach(userDTO -> {
            User user = new User();
            user.setName(userDTO.name());
            user.setCpf(userDTO.cpf());
            user.setEmail(userDTO.email());
            user.setPassword(userDTO.password());
            user.setRole(userDTO.role());
            user.setCompanyName(userDTO.companyName());
            user.setCompanies(companies);
            users.add(user);
        });

        return users;
    }


    private boolean verifyLocal(CompanyDTO companyDTO ){
        try {
            for(LocalDTO localDTO : companyDTO.locals()){
                if(!localService.verifyAddressAndCityAndState(localDTO.address(), localDTO.city(), localDTO.state())){
                    return false;
                }

            }
            return true;

        }catch(Exception e){
            logger.error("verifyLocal erro " + e.getMessage());
            throw new IllegalArgumentException("Locals must not be empty.");
        }


    }

    private boolean verifyUser(CompanyDTO companyDTO ){
        try {
            for(UserDTO user : companyDTO.user()){
                if(!userService.findByCpf(user.cpf())){
                    return false;
                }

            }
            return true;
        }catch(Exception e){
            logger.error("verifyUser erro" + e.getMessage());
            throw new IllegalArgumentException("User must not be empty.");
        }

    }


}
