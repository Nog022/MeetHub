package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.dto.CompanyDTO;
import com.git.Nog022.MeetHub.dto.JoinCompanyDTO;
import com.git.Nog022.MeetHub.dto.UserDTO;
import com.git.Nog022.MeetHub.entity.Company;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO save(@RequestBody @Validated CompanyDTO companyDTO) {
        return companyService.save(companyDTO);
    }
    //TODO Fazer um DTO para sair todas as informações corretamente
    @GetMapping("/findUsersCompany/{cnpj}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserDTO> findUsers(@PathVariable String cnpj) {
        return companyService.findAllUsers(cnpj);
    }



    //TODO fazer um metodo para entrar em empresas que não possuem dominio
//    @PostMapping("/joinCompany")
//    @ResponseStatus(HttpStatus.CREATED)
//    public String join(@RequestBody @Validated JoinCompanyDTO dto) {
//        return companyService.joinCompanyWithDomain(dto);
//    }
}
