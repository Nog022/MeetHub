package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.dto.LocalDTO;
import com.git.Nog022.MeetHub.dto.RoomDTO;
import com.git.Nog022.MeetHub.dto.ViaCepResponseDTO;
import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.service.LocalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/locations")
@Tag(name = "Localização", description = "Gerenciamento de localizações de salas de reunião")
public class LocalController {

    @Autowired
    private LocalService localService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Criar uma nova localização",
            description = "Cria um novo registro de localização no sistema"
    )
    public ResponseEntity<LocalDTO> save(@RequestBody @Validated LocalDTO local) {
        return localService.save(local);
    }

    @GetMapping("/listLocal")
    @Operation(
            summary = "Listar todas as localizações",
            description = "Retorna uma lista com todas as localizações cadastradas"
    )

    public List<Local> listLocal() {
        return localService.listLocal();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Excluir uma localização",
            description = "Exclui a localização pelo seu ID"
    )

    public void delete(@PathVariable Long id) {
        localService.delete(id);
    }

    @GetMapping("/localById/{id}")
    @Operation(
            summary = "Buscar localização por ID",
            description = "Retorna os detalhes de uma localização pelo seu ID"
    )

    public LocalDTO localById(@PathVariable Long id) {
        return localService.localDTOById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Atualizar uma localização existente",
            description = "Atualiza as informações de uma localização existente"
    )

    public void update(@RequestBody @Validated LocalDTO local) {
        localService.update(local);
    }

    @GetMapping("/findAddressByZipCode/{cep}")
    @Operation(
            summary = "Buscar localização por CEP",
            description = "Retorna os detalhes de uma localização pelo seu CEP"
    )

    public ViaCepResponseDTO findAddressByZipCode(@PathVariable String cep) {
        return localService.findAddressByZipCode(cep);
    }

    @GetMapping("/listLocalByInstitution/{id}")
    @Operation(
            summary = "Listar localizações por instituição",
            description = "Retorna uma lista de todas as localizações cadastradas de uma instituição específica pelo seu ID"
    )
    public List<LocalDTO> listLocalByInstitution(@PathVariable Long id) {
        return localService.listLocalByInstitution(id);
    }
}
