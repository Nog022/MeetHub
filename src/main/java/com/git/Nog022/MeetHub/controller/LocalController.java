package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.service.LocalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public Local save(@RequestBody @Validated Local local) {
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
    public void delete(@PathVariable Integer id) {
        localService.delete(id);
    }

    @GetMapping("/localById/{id}")
    @Operation(summary = "Get a local by ID", description = "Returns the details of a local by its ID")
    public Local localById(@PathVariable Integer id) {
        return localService.localById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update an existing local", description = "Updates the information of an existing local")
    public void update(@RequestBody @Validated Local local) {
        localService.update(local);
    }


}
