package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    public static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody @Validated User user){
        logger.info("entrou save");
        return userService.save(user);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id){
        userService.delete(id);


    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,@RequestBody @Validated User user){
        userService.update(id,user);

    }

    @GetMapping("/listAll")
    public List<User> listUser(){
        return userService.listUser();
    }

}
