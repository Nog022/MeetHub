package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
@Data
@Entity
public class Room {
    @Id
    private Integer id;
    private String name;
    private Integer capacity;
    private String place;
    private String availableResources;
}
