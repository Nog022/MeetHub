package com.git.Nog022.MeetHub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;

import java.util.List;

@Data
@Entity
@Table(name = "INSTITUTION")
public class Institution  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INSTITUTION_ID")
    private Long id;

    @Column(name = "INSTITUTION_NAME", length = 255)
    @NotNull
    @NotEmpty
    private String name;

    @Column(name = "CNPJ", length = 14, nullable = false, updatable = false)
    @NotNull
    @NotEmpty
    @CNPJ(message = "invalid cnpj")
    private String cnpj;

    @ElementCollection
    @Column(name = "DOMAIN", length = 255, unique = true)
    private List<String> domains;


    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Local> locals;

    @OneToMany(mappedBy = "institution")
    private List<User> users;

}
