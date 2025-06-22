package com.git.Nog022.MeetHub.dto;

import java.util.List;

public record RoomDTO(
        String name,
        Integer capacity,
        Long localId,
        List<String> resources
) {
}