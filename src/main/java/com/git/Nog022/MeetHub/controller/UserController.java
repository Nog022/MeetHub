package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/hello")
    public String hello (){
        return "Está funcionando";
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated User user){
        return userRepository.save(user);
    }

    @GetMapping("/listAll")
    public List<User> listUser(){
        return userRepository.findAll();
    }

}
