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
@Tag(name = "Local", description = "Gerenciamento de salas de reunião")
public class LocalController {

    @Autowired
    private LocalService localService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new local", description = "Creates a new local entry in the system")
    public ResponseEntity<LocalDTO> save(@RequestBody @Validated LocalDTO local) {
        return localService.save(local);
    }

    @GetMapping("/listLocal")
    @Operation(summary = "List all locals", description = "Returns a list of all registered locals")
    public List<Local> listLocal() {
        return localService.listLocal();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a local", description = "Deletes the local by its ID")
    public void delete(@PathVariable Long id) {
        localService.delete(id);
    }

    @GetMapping("/localById/{id}")
    @Operation(summary = "Get a local by ID", description = "Returns the details of a local by its ID")
    public LocalDTO localById(@PathVariable Long id) {
        return localService.localDTOById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing local", description = "Updates the information of an existing local")
    public void update(@RequestBody @Validated LocalDTO local) {
        localService.update(local);
    }

    @GetMapping("/findAddressByZipCode/{cep}")
    @Operation(summary = "Get a info by cep", description = "Returns the details of a local by its cep")
    public ViaCepResponseDTO findAddressByZipCode(@PathVariable String cep) {
        return localService.findAddressByZipCode(cep);
    }

    @GetMapping("/listLocalByInstitution/{id}")
    //@Operation(summary = "List all rooms", description = "Returns a list of all registered rooms")
    public List<LocalDTO> listLocalByInstitution(@PathVariable Long id) {
        return localService.listLocalByInstitution(id);
    }
}
