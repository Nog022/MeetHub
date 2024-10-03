package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROOM_ID")
    private Integer id;
    @Column(name = "name_room", length = 255)
    private String name;
    @Column(name = "capacity", length = 255)
    private Integer capacity;
    @Column(name = "place", length = 255)
    private String place;
    @Column(name = "resources", length = 255)
    private String resources;
}
