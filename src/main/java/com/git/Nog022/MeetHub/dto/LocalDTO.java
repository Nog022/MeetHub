package com.git.Nog022.MeetHub.dto;

import com.git.Nog022.MeetHub.enums.RoomBlockType;
import com.git.Nog022.MeetHub.enums.RoomLocationType;

public record LocalDTO(
        String name,
        String address,
        String city,
        String state,
        RoomLocationType roomLocationType,
        RoomBlockType roomBlockType,
        Integer companyId
) {
}