package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    public static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    UserRepository userRepository;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated User user){
        return userRepository.save(user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id){
        userRepository.findById(id).map(userId -> {
            userRepository.delete(userId);
            return userId;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));


    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Integer id,@RequestBody @Validated User user){

        User userOtional = userRepository.findById(user.getId()).map(
                userFind -> {
                    user.setId(userFind.getId());
                    user.setCpf(userFind.getCpf());
                    user.setName(userFind.getName());
                    user.setEmail(userFind.getEmail());
                    user.setPassword(userFind.getPassword());
                    user.setCompanyName(userFind.getCompanyName());
                    return userFind;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));

    }

    @GetMapping("/listAll")
    public List<User> listUser(){
        return userRepository.findAll();
    }

}
