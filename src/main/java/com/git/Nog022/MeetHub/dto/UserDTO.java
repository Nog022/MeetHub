package com.git.Nog022.MeetHub.dto;

import com.git.Nog022.MeetHub.enums.UserRole;


public record UserDTO(
        String name,
        String email,
        String cpf,
        String companyName,
        UserRole role
) {}
