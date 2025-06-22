package com.git.Nog022.MeetHub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID", nullable = false)
    @JsonBackReference
    private Room room;

    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "end_time")
    private LocalTime endTime;
    @Column(name = "event_description", length = 255)
    private String eventDescription;
}
