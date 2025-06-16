package com.git.Nog022.MeetHub.entity;

import com.git.Nog022.MeetHub.enums.RoomBlockType;
import com.git.Nog022.MeetHub.enums.RoomLocationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "LOCATIONS")
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LOCATION_ID")
    private Integer id;

    @Column(name = "CEP", length = 10)
    private String cep;

    @Column(name = "NAME", length = 255)
    @NotEmpty
    private String name;

    @Column(name = "ADDRESS", length = 255)
    @NotEmpty
    private String address;

    @Column(name = "CITY", length = 100)
    @NotEmpty
    private String city;

    @Column(name = "NEIGHBORHOOD", length = 100)
    private String neighborhood;

    @Column(name = "NUMBER", length = 10)
    @NotEmpty
    private String number;

    @Column(name = "STATE", length = 100)
    @NotEmpty
    private String state;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;




}
