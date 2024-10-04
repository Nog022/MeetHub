package com.git.Nog022.MeetHub.service;

import com.git.Nog022.MeetHub.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface UserService {
    User save(User user);

    void delete(Integer id);

    void update(Integer id, User user);

    List<User> listUser();
}
