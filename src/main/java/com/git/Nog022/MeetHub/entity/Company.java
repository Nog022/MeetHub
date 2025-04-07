package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "COMPANY")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_ID")
    private Long id;

    @Column(name = "company_name", length = 255)
    @NotNull
    @NotEmpty
    private String name;

    @Column(name = "domain", length = 255)
    private String domain;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Local> locals;

    @ManyToMany(mappedBy = "companies")
    private List<User> users;

}
