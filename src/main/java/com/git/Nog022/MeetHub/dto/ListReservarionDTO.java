package com.git.Nog022.MeetHub.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record ListReservarionDTO(
        Integer id,
        String personName,
        RoomReportDTO roomReportDTO,
        LocalDateTime date,
        LocalTime startTime,
        LocalTime endTime,
        String eventDescription,
        UserDTO userDTO
) {
}
