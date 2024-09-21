package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class Reservation {
    @Id
    private Integer id;
    private String personName;
    private String chosenRoom;
    private LocalDateTime date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String eventDescription;
}
