package com.git.Nog022.MeetHub.dto;

import java.util.List;

public record RoomDTO(
        String name,
        Integer capacity,
        Integer localId,
        List<String> resources
) {
}