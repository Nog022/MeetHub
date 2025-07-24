package com.git.Nog022.MeetHub.dto;

import com.git.Nog022.MeetHub.entity.Company;

import java.util.List;

public record CompanyResponseDTO(
        Long id,
        String cnpj,
        String name,
        List<String> domain,
        String token

) {
}
