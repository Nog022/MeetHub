package com.git.Nog022.MeetHub.dto;



import java.util.List;

public record InstitutionResponseDTO(
        Long id,
        String cnpj,
        String name,
        List<String> domain,
        String token

) {
}
