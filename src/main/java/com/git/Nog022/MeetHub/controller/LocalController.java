package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/locations")
public class LocalController {

    @Autowired
    private LocalService localService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Local save(@RequestBody @Validated Local local){
        return localService.save(local);
    }

    @GetMapping("/listLocal")
    public List<Local> listRoom(){
        return localService.listLocal();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        localService.delete(id);
    }

    @GetMapping("/localById/{id}")
    public Local localById(@PathVariable Integer id){
        return localService.localById(id);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Validated Local local){
        localService.update(local);
    }


}
