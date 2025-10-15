package com.git.Nog022.MeetHub.controller;


import com.git.Nog022.MeetHub.dto.InstitutionDTO;
import com.git.Nog022.MeetHub.dto.InstitutionResponseDTO;
import com.git.Nog022.MeetHub.dto.RoomDTO;
import com.git.Nog022.MeetHub.dto.UserDTO;


import com.git.Nog022.MeetHub.service.InstitutionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/institution")
@Slf4j
public class InstitutionController {

    @Autowired
    private InstitutionService institutionService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@RequestBody @Validated InstitutionDTO institutionDTO) {
        return institutionService.save(institutionDTO);
    }

    //TODO Fazer um DTO para sair todas as informações corretamente
    @GetMapping("/findUsersInstitution/{cnpj}")
    @ResponseStatus(HttpStatus.CREATED)
    public List<UserDTO> findUsers(@PathVariable String cnpj) {
        return institutionService.findAllUsers(cnpj);
    }


    @PutMapping("/update")
    @Operation(summary = "Update institution ", description = "Updates the institution")
    public ResponseEntity<?> update(@RequestBody @Validated InstitutionDTO institutionDTO) {
        return institutionService.update(institutionDTO);
    }


    //fazer um metodo para entrar em empresas que não possuem dominio
    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.CREATED)
    public void join(@RequestBody @Validated String email) {
        institutionService.joinInstitutionWithDomain(email);
    }


}
