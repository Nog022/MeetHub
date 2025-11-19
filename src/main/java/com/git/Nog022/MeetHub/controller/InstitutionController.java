package com.git.Nog022.MeetHub.controller;


import com.git.Nog022.MeetHub.dto.InstitutionDTO;
import com.git.Nog022.MeetHub.dto.InstitutionResponseDTO;
import com.git.Nog022.MeetHub.dto.RoomDTO;
import com.git.Nog022.MeetHub.dto.UserDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import com.git.Nog022.MeetHub.service.InstitutionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/institution")
@Slf4j
@Tag(name = "Instituição", description = "Gerenciamento de instituições")
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar uma nova instituição",
            description = "Cria um novo registro de instituição no sistema"
    )
    public InstitutionResponseDTO save(@RequestBody @Validated InstitutionDTO institutionDTO) {
        return institutionService.save(institutionDTO);
    }

    //TODO Fazer um DTO para sair todas as informações corretamente
    @GetMapping("/findUsersInstitution/{cnpj}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Listar usuários por instituição",
            description = "Retorna uma lista de todos os usuários cadastrados de uma instituição pelo seu CNPJ"
    )
    public List<UserDTO> findUsers(@PathVariable String cnpj) {
        return institutionService.findAllUsers(cnpj);
    }

    @PutMapping("/update")
    @Operation(
            summary = "Atualizar instituição",
            description = "Atualiza os dados de uma instituição existente"
    )
    public InstitutionDTO update(@RequestBody @Validated InstitutionDTO institutionDTO) {
        InstitutionDTO institution = institutionService.update(institutionDTO);
        log.info("Instituição atualizada: " + institution.toString());
        return institution;
    }

//    //fazer um metodo para entrar em empresas que não possuem dominio
//    @PostMapping("/refresh")
//    @ResponseStatus(HttpStatus.CREATED)
//    @Operation(
//            summary = "Associar usuário a instituição",
//            description = "Permite que um usuário entre em uma instituição que não possui domínio definido, utilizando seu e-mail"
//    )
//    public void join(@RequestBody @Validated String email) {
//        institutionService.joinInstitutionWithDomain(email);
//    }


}
