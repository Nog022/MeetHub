package com.git.Nog022.MeetHub.dto;

import com.git.Nog022.MeetHub.entity.Company;
import com.git.Nog022.MeetHub.enums.UserRole;

public record LoginResponseDTO(
        String token,
        String name,
        String email,
        String cpf,
        UserRole role,
        boolean emailVerificado,
        CompanyDTO company
) {
}