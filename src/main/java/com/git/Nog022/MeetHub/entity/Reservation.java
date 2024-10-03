package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESERVATION_ID")
    private Integer id;
    @Column(name = "person_name", length = 255)
    private String personName;
    @Column(name = "chosen_room", length = 255)
    private String chosenRoom;
    @Column(name = "date_time")
    private LocalDateTime date;
    @Column(name = "ROOM_ID")
    private Integer room_id;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
    @Column(name = "event_description", length = 255)
    private String eventDescription;
}
