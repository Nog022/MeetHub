package com.git.Nog022.MeetHub.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationDTO(
        String personName,
        Long roomId,
        LocalDateTime date,
        LocalTime startTime,
        LocalTime endTime,
        String eventDescription,
        Long institutionId
) {
}
