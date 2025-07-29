package com.git.Nog022.MeetHub.dto;

import com.git.Nog022.MeetHub.enums.UserRole;


public record UserDTO(
        Long id,
        String name,
        String email,
        String cpf,
        UserRole role
) {}
