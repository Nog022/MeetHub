package com.git.Nog022.MeetHub.dto;

import java.util.List;

public record InstitutionDTO(
        Long id,
        String cnpj,
        String name,
        List<String> domain,
        String userEmail

) {
}
