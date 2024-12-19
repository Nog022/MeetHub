package com.git.Nog022.MeetHub.entity;

import com.git.Nog022.MeetHub.enums.RoomBlockType;
import com.git.Nog022.MeetHub.enums.RoomLocationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOCATION_ID")
    private Integer id;

    @Column(name = "NAME", length = 255)
    @NotEmpty
    private String name;

    @Column(name = "ADDRESS", length = 255)
    @NotEmpty
    private String address;

    @Column(name = "CITY", length = 100)
    @NotEmpty
    private String city;

    @Column(name = "STATE", length = 100)
    @NotEmpty
    private String state;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROOM_TYPE")
    private RoomLocationType roomLocationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROOM_BLOCK_TYPE")
    private RoomBlockType roomBlockType;



}
