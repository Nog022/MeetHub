package com.git.Nog022.MeetHub.repository;

import com.git.Nog022.MeetHub.entity.User;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByCpf(String cpf);

    Optional<User> findByEmail(@NotEmpty(message = "{field.required}") String email);
}
