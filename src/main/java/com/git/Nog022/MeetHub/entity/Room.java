package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Room {
    private String name;
    private Integer capacity;
    private String place;
    private String availableResources;
}
