package com.git.Nog022.MeetHub.dto;

import java.util.List;

public record RoomDetailDTO(
        Long id,
        String name,
        Integer capacity,
        String localName,
        List<String> resources
) {
}
