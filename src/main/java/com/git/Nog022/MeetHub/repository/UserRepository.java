package com.git.Nog022.MeetHub.repository;

import com.git.Nog022.MeetHub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends JpaRepository<User,Integer> {
}
