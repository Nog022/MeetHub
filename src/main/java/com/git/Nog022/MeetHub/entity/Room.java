package com.git.Nog022.MeetHub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ROOMS")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROOM_ID")
    private Long id;
    @Column(name = "NAME_ROOM", length = 255)
    @NotEmpty(message = "{field.required}")
    private String name;
    @Column(name = "CAPACITY", length = 255)
    @NotNull(message = "{capacity.null}")
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "idPlace", referencedColumnName = "LOCATION_ID", nullable = false)
    private Local local;

    @ElementCollection
    @CollectionTable(name = "room_resources", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "RESOURCE")
    private List<String> resources = new ArrayList<>();


    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Reservation> reservations;

    @Column(name = "LAST_RESERVATION_ID")
    private Integer lastReservationId;

}
