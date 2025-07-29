package com.git.Nog022.MeetHub.controller;


import com.git.Nog022.MeetHub.dto.InstitutionDTO;
import com.git.Nog022.MeetHub.dto.InstitutionResponseDTO;
import com.git.Nog022.MeetHub.dto.UserDTO;


import com.git.Nog022.MeetHub.service.InstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/institution")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public InstitutionResponseDTO save(@RequestBody @Validated InstitutionDTO institutionDTO) {
        return institutionService.save(institutionDTO);
    }

    //TODO Fazer um DTO para sair todas as informações corretamente
    @GetMapping("/findUsersInstitution/{cnpj}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserDTO> findUsers(@PathVariable String cnpj) {
        return institutionService.findAllUsers(cnpj);
    }



     //fazer um metodo para entrar em empresas que não possuem dominio
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public void join(@RequestBody @Validated String email) {
        institutionService.joinInstitutionWithDomain(email);
    }
}
