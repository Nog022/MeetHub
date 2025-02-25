package com.git.Nog022.MeetHub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

public record RegisterDTO(

       // @NotEmpty(message = "{field.required}")
      //  @Size(max = 100, message = "{field.maxLength}")
        String name,

//        @NotEmpty(message = "{field.required}")
//        @Email(message = "{invalid.email}")
//        @Size(max = 100, message = "{field.maxLength}")
        String email,

//        @NotEmpty(message = "{field.required}")
//        @CPF(message = "{invalid.cpf}")
//        @Size(min = 11, max = 11, message = "{field.cpfLength}")
        String cpf,
//
//        @NotEmpty(message = "{field.required}")
//        @Size(min = 6, message = "{field.passwordLength}")
        String password,

//        @NotEmpty(message = "{field.required}")
//        @Size(max = 255, message = "{field.maxLength}")
        String companyName
) {

}
