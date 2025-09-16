package com.git.Nog022.MeetHub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
public class ReservationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer reservationId;
    private Long userId;
    private String personName;
    private LocalDateTime date;
    private Long roomId;
    private LocalTime startTime;
    private LocalTime endTime;
    private String eventDescription;
    private Long institutionId;



}
