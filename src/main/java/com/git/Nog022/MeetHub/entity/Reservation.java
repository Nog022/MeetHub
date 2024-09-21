package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Reservation {
    private String personName;
    private String chosenRoom;
    private LocalDateTime date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String eventDescription;
}
