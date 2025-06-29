package com.git.Nog022.MeetHub.dto;

import java.util.List;

public record RoomReportDTO(
        Long id,
        String name,
        Integer capacity,
        LocalDTO localDTO,
        List<String> resources
) {
}
