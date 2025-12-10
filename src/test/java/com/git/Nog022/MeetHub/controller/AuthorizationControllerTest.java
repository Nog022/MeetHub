package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.config.TokenService;
import com.git.Nog022.MeetHub.dto.AuthorizationDTO;
import com.git.Nog022.MeetHub.dto.LoginResponseDTO;
import com.git.Nog022.MeetHub.dto.RegisterDTO;
import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.enums.UserRole;
import com.git.Nog022.MeetHub.repository.UserRepository;
import com.git.Nog022.MeetHub.service.EmailService;
import com.git.Nog022.MeetHub.service.InstitutionService;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class AuthorizationControllerTest {

    @InjectMocks
    private AuthorizationController authorizationController;

    @Mock
    private UserRepository usuarioRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private EmailService emailService;

    @Mock
    private InstitutionService institutionService;

    private User user;

    private RegisterDTO registerDTO;

    private AuthorizationDTO authorizationDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Rodrigo");
        user.setEmail("rodrigo@mail.com");
        user.setCpf("00011122233");
        user.setPassword("123");
        user.setRole(UserRole.USER);
        user.setEmailVerificado(true);

        registerDTO = new RegisterDTO("Rodrigo", "new@gmail.com", "12345678901", "123");
        authorizationDTO = new AuthorizationDTO("rodrigo@gmail.com", "123");
    }




    @Test
    void loginSuccess() {


        when(usuarioRepository.findByEmail(authorizationDTO.email())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(
                new UsernamePasswordAuthenticationToken(user, null)
        );
        when(tokenService.generateToken(user)).thenReturn("TOKEN123");

        ResponseEntity<?> response = authorizationController.login(authorizationDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        LoginResponseDTO body = (LoginResponseDTO) response.getBody();
        assertEquals("TOKEN123", body.token());
        verify(tokenService).generateToken(user);
    }

    @Test
    void loginUserNotFound() {


        when(usuarioRepository.findByEmail(authorizationDTO.email())).thenReturn(Optional.empty());

        ResponseEntity<?> response = authorizationController.login(authorizationDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void loginEmailNotVerified() {

        user.setEmailVerificado(false);

        when(usuarioRepository.findByEmail(authorizationDTO.email())).thenReturn(Optional.of(user));

        ResponseEntity<?> response = authorizationController.login(authorizationDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void loginAuthenticationFailure() {
        when(usuarioRepository.findByEmail(authorizationDTO.email())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Falha"));

        ResponseEntity<?> response = authorizationController.login(authorizationDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void registerSuccess() {
        when(usuarioRepository.findByEmail(registerDTO.email())).thenReturn(Optional.empty());
        when(emailService.gerarToken(registerDTO.email())).thenReturn("TOKEN123");
        when(usuarioRepository.save(any())).thenReturn(user);

        ResponseEntity<User> response = authorizationController.register(registerDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(emailService).enviarEmailConfirmacao(eq(registerDTO.email()), any());
    }

    @Test
    void registerEmailAlreadyRegistered() {
        when(usuarioRepository.findByEmail(registerDTO.email())).thenReturn(Optional.of(user));
        ResponseEntity<User> response = authorizationController.register(registerDTO);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void checkEmailSuccess() {
        user.setEmailVerificado(false); // ← ESSENCIAL!

        when(emailService.validarToken("TOKEN123"))
                .thenReturn("rodrigo@gmail.com");

        when(usuarioRepository.findByEmail("rodrigo@gmail.com"))
                .thenReturn(Optional.of(user));

        when(usuarioRepository.save(any(User.class)))
                .thenReturn(user);

        ResponseEntity<String> response =
                authorizationController.confirmarEmail("TOKEN123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("E-mail confirmado com sucesso!", response.getBody());

        verify(usuarioRepository).save(any(User.class));
    }


    @Test
    void checkEmailAlreadyVerified() {
        user.setEmailVerificado(true);

        when(emailService.validarToken("TOKEN123")).thenReturn("rodrigo@mail.com");
        when(usuarioRepository.findByEmail("rodrigo@mail.com")).thenReturn(Optional.of(user));

        ResponseEntity<String> response = authorizationController.confirmarEmail("TOKEN123");

        assertEquals("E-mail já foi confirmado.", response.getBody());
    }

    @Test
    void checkEmailTokenExpired() {
        when(emailService.validarToken("TOKEN123")).thenThrow(new ExpiredJwtException(null, null, "exp"));

        ResponseEntity<String> response = authorizationController.confirmarEmail("TOKEN123");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Token expirado.", response.getBody());
    }

    @Test
    void checkEmailTokenInvalid() {
        when(emailService.validarToken("TOKEN123")).thenThrow(new RuntimeException());

        ResponseEntity<String> response = authorizationController.confirmarEmail("TOKEN123");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Token inválido.", response.getBody());
    }

}