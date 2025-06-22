package com.git.Nog022.MeetHub.dto;

import com.git.Nog022.MeetHub.entity.Local;
import com.git.Nog022.MeetHub.entity.User;

import java.util.List;

public record CompanyDTO(
        Long id,
        String cnpj,
        String name,
        List<String> domain,
        String userEmail

) {
}
