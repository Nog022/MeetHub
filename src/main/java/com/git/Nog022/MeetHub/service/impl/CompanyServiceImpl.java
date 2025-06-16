package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.config.SecurityConfig;
import com.git.Nog022.MeetHub.dto.*;
import com.git.Nog022.MeetHub.entity.Company;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.enums.UserRole;
import com.git.Nog022.MeetHub.exception.ValidationException;
import com.git.Nog022.MeetHub.repository.CompanyRepository;
import com.git.Nog022.MeetHub.service.CompanyService;
import com.git.Nog022.MeetHub.service.LocalService;
import com.git.Nog022.MeetHub.service.UserService;
import com.git.Nog022.MeetHub.service.ValidateDomainService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {

    public static Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);


    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private ValidateDomainService validateDomainService;


    @Override
    public CompanyDTO save(CompanyDTO companyDTO) {
        logger.info("entrou em salvar company");
        User user = userService.findByEmail(companyDTO.userEmail());


        if(user != null && companyRepository.findByCnpj(companyDTO.cnpj()) == null) {
            logger.info("entrou no if");

            Company company = new Company();
            company.setCnpj(companyDTO.cnpj());
            company.setName(companyDTO.name());
            company.setLocals(null);
            company.setUsers(new ArrayList<>());
            company.getUsers().add(setUserDTO(user, company));
            company.setDomains(companyDTO.domain() == null ? Collections.emptyList() : new ArrayList<>(companyDTO.domain()));
            companyRepository.save(company);
            userService.save(user);
            return companyDTO;

        }

        throw new ValidationException("Erro Save Company");
    }


    @Override
    public void joinCompanyWithDomain(User user) {
        try {
            Optional<Company> optionalCompany = validateDomainService.validateDomain(user.getEmail());

            if (optionalCompany.isPresent()) {
                logger.info("Empresa encontrada");
                Company company = optionalCompany.get();
                company.getUsers().add(user);
                user.getCompanies().add(company);
                userService.save(user);
                companyRepository.save(company);

            }else{
                logger.info("Not found company with email " + user.getEmail());
            }

        } catch (Exception e) {
            logger.error("joinCompany erro " + e.getMessage());
            throw new RuntimeException("Erro while processing join company") ;
        }
    }

    //TODO terminar a parte do refresh
    @Override
    public void joinCompanyWithDomain(String email) {
        try {
            logger.info("joinCompanyWithDomain()");
            User user = userService.findByEmail(email);

            logger.info("User: " + user);
            Optional<Company> optionalCompany = validateDomainService.validateDomain(email);
            logger.info("Company encontrada: "  + optionalCompany);

            if (optionalCompany.isPresent() && user.getCompanies().isEmpty()) {
                logger.info("Empresa encontrada");
                Company company = optionalCompany.get();
                company.getUsers().add(user);
                user.getCompanies().add(company);
                userService.save(user);
                companyRepository.save(company);

            }else{
                logger.info("The email " + user.getEmail() + " is not valid");
            }

        } catch (Exception e) {
            logger.error("joinCompany erro " + e.getMessage());
            throw new RuntimeException("Erro while processing join company") ;
        }
    }




    @Override
    public List<UserDTO> findAllUsers(String cnpj) {
        Company company = companyRepository.findByCnpj(cnpj);
        List<User> users = company.getUsers();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO dto = new UserDTO(
                    user.getName(),
                    user.getEmail(),
                    user.getCpf(),
                    user.getRole()

            );
            userDTOs.add(dto);
        }

        return userDTOs;
    }



//    private List<Local> setLocalDTO(CompanyDTO companyDTO, Company company) {
//        logger.info("entrou em setLocalDTO");
//        List<Local> locals = new ArrayList<>();
//        companyDTO.locals().forEach(localDTO -> {
//            Local local = new Local();
//            local.setName(localDTO.name());
//            local.setAddress(localDTO.address());
//            local.setCity(localDTO.city());
//            local.setState(localDTO.state());
//            local.setRoomBlockType(localDTO.roomBlockType());
//            local.setRoomLocationType(localDTO.roomLocationType());
//            local.setCompany(company);
//            locals.add(local);
//        });
//
//        return locals;
//    }

    private User setUserDTO(User user, Company company) {
        logger.info("entrou em setUserDTO");
        user.getCompanies().add(company);
        user.setRole(UserRole.ADMIN);
        return user;
    }


//    private boolean verifyLocal(CompanyDTO companyDTO ){
//        try {
//            for(LocalDTO localDTO : companyDTO.locals()){
//                if(!localService.verifyAddressAndCityAndState(localDTO.address(), localDTO.city(), localDTO.state())){
//                    return false;
//                }
//
//            }
//            return true;
//
//        }catch(Exception e){
//            logger.error("verifyLocal erro " + e.getMessage());
//            throw new IllegalArgumentException("Locals must not be empty.");
//        }
//
//
//    }



//    private boolean companyExistsByUserEmail(String email){
//        logger.info("entrou do domain");
//
//
//        String domain = email.substring(email.indexOf("@") + 1);
//        return companyRepository.existsByDomain(domain);
//
//
//
//
//    }

//    private boolean domainIsValid(Company company){
//        if(companyRepository.findByDomain(company.getDomain()).isEmpty());
//
//
//
//    }






}
