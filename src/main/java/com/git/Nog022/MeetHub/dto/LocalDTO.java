package com.git.Nog022.MeetHub.dto;

import com.git.Nog022.MeetHub.enums.RoomBlockType;
import com.git.Nog022.MeetHub.enums.RoomLocationType;

public record LocalDTO(
        Long id,
        String name,
        String cep,
        String address,
        String neighborhood,
        String city,
        String state,
        String number,
        String complement,
        Long companyId
) {
}