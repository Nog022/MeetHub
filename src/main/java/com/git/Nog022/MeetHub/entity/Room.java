package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
@Entity
@Table(name = "ROOMS")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROOM_ID")
    private Integer id;
    @Column(name = "name_room", length = 255)
    @NotEmpty(message = "{field.required}")
    private String name;
    @Column(name = "capacity", length = 255)
    @NotNull(message = "{capacity.null}")
    private Integer capacity;
    @Column(name = "place", length = 255)
    @NotEmpty(message = "{field.required}")
    private String place;
    @Column(name = "resources", length = 255)
    private String resources;
}
