package com.git.Nog022.MeetHub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESERVATION_ID")
    private Integer id;
    @Column(name = "PERSON_NAME", length = 255)
    private String personName;

    @Column(name = "DATE_TIME")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "ROOM_ID", referencedColumnName = "ROOM_ID", nullable = false)
    @JsonBackReference
    private Room room;

    @Column(name = "START_TIME")
    private LocalTime startTime;
    @Column(name = "END_TIME")
    private LocalTime endTime;
    @Column(name = "EVENT_DESCRIPTION", length = 255)
    private String eventDescription;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "INSTITUTION_ID")
    private Institution institution;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
