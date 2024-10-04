package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.repository.UserRepository;
import com.git.Nog022.MeetHub.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void delete(Integer id){
        userRepository.findById(id).map(userId -> {
            userRepository.delete(userId);
            return userId;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));

    }


    @Transactional
    public User save(User user){
        return userRepository.save(user);
    }

    @Transactional
    public void update(Integer id, User user){
        userRepository.findById(id).map(
                userFind -> {
                    user.setId(userFind.getId());
                    userRepository.save(user);
                    return userFind;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario nao encontrado"));

    }
    @Transactional
    public List<User> listUser(){
        return userRepository.findAll();
    }
}
