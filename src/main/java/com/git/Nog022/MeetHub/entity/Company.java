package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

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

    @Column(name = "cnpj", length = 14, nullable = false, updatable = false)
    @NotNull
    @NotEmpty
    @CNPJ(message = "invalid cnpj")
    private String cnpj;

    @ElementCollection
    @Column(name = "domain", length = 255, unique = true)
    private List<String> domains;


    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Local> locals;

    @OneToMany(mappedBy = "company")
    private List<User> users;

}
