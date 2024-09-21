package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME", length = 100)
    private String name;
    @Column(name = "EMAIL", length = 100)
    private String email;
    @Column(name = "CPF", length = 11)
    private String cpf;
    @Column(name = "PASSWORD", length = 100)
    private String password;
    @Column(name = "COMPANYNAME", length = 100)
    private String companyName;

}
