package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.repository.LocalRepository;
import com.git.Nog022.MeetHub.service.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}
