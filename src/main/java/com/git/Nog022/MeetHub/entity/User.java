package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Data
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Integer id;
    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "EMAIL", length = 100)
    @NotEmpty(message = "{field.required}")
    private String email;

    @Column(name = "CPF", length = 11,unique = true)
    @NotEmpty(message = "{field.required}")
    @CPF(message = "{invalid.cpf}")
    private String cpf;

    @Column(name = "PASSWORD", length = 100)
    private String password;
    @Column(name = "COMPANYNAME", length = 255)
    @NotEmpty(message = "{field.required}")
    private String companyName;


}
