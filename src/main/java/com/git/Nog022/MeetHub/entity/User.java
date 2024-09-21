package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class User {
    private String name;
    private String email;
    private Integer cpf;
    private String password;
    private String companyName;

}
