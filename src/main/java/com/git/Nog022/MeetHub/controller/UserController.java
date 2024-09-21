package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/save")
    public void save(User user){
        userRepository.save(user);
    }
}
