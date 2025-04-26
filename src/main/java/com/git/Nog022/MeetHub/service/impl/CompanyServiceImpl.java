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
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public CompanyDTO save(CompanyDTO companyDTO) {
        logger.info("entrou em salvar company");
        User user = userService.findByEmail(companyDTO.userEmail());

        if(verifyLocal(companyDTO) && user != null) {
            logger.info("entrou no if");

            Company company = new Company();
            company.setCnpj(companyDTO.cnpj());
            company.setName(companyDTO.name());
            company.setLocals(setLocalDTO(companyDTO, company));
            company.setUsers(new ArrayList<>());
            company.getUsers().add(setUserDTO(user, company));
            company.setDomain(companyDTO.domain() == null ? null : companyDTO.domain());
            companyRepository.save(company);
            userService.save(user);
            return companyDTO;

        }

        throw new ValidationException("Erro Save Company");
    }

    //TODO terminar metodo
    @Override
    public void joinCompanyWithDomain(User user) {
        try {
            logger.info("entrou em join company");
            String domain = user.getEmail().substring(user.getEmail().indexOf("@") + 1);
            logger.info("domain: " + domain);
            Optional<Company> optionalCompany = companyRepository.findByDomainIgnoreCase(domain);

            if (optionalCompany.isPresent()) {
                logger.error("Empresa encontrada");
                Company company = optionalCompany.get();
                company.getUsers().add(user);
                user.getCompanies().add(company);
                userService.save(user);
                companyRepository.save(company);

            }

            logger.info("Nenhuma empresa encontrada com o domínio: " + domain);


        } catch (Exception e) {

            logger.error("joinCompany erro " + e.getMessage());
            throw new RuntimeException("Erro while processing join company") ;
        }
    }

    @Override
    public List<UserDTO> findAllUsers(String cnpj) {
        Company company = companyRepository.findByCnpj(cnpj);
        List<User> users = company.getUsers(); // assumindo que é uma lista!
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO dto = new UserDTO(
                    user.getName(),
                    user.getEmail(),
                    user.getCpf(),
                    user.getCompanyName(),
                    user.getRole()

            );
            userDTOs.add(dto);
        }

        return userDTOs;
    }



    private List<Local> setLocalDTO(CompanyDTO companyDTO, Company company) {
        logger.info("entrou em setLocalDTO");
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

    private User setUserDTO(User user, Company company) {
        logger.info("entrou em setUserDTO");
        user.getCompanies().add(company);
        user.setRole(UserRole.ADMIN);
        return user;
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
