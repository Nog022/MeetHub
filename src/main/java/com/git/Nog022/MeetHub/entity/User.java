package com.git.Nog022.MeetHub.entity;

import com.git.Nog022.MeetHub.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Integer id;
    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "EMAIL", length = 100,unique = true)
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

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String name, String email, String cpf, String password, String companyName, UserRole role) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.password = password;
        this.companyName = companyName;
        this.role = role;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + this.role.name());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
