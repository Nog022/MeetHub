package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.config.TokenService;
import com.git.Nog022.MeetHub.dto.*;
import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.enums.UserRole;
import com.git.Nog022.MeetHub.repository.UserRepository;
import com.git.Nog022.MeetHub.service.CompanyService;
import com.git.Nog022.MeetHub.service.EmailService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthorizationController {

    public static Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CompanyService companyService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Validated AuthorizationDTO data) {
        try {
            logger.info("Iniciando login");
            logger.info("Dados recebidos: Email: {}, Senha: {}", data.email(), data.password());


            User user = this.usuarioRepository.findByEmail(data.email())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário not fould"));


            if (!user.isEmailVerificado()) {
                logger.warn("Login attempt with unverified email: {}", data.email());
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponseDTO("Email not verified. Please check your inbox."));
            }


            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            logger.info("Token de autenticação gerado: {}", usernamePassword);


            var auth = this.authenticationManager.authenticate(usernamePassword);
            logger.info("Autenticação bem-sucedida");


            var usuario = (User) auth.getPrincipal();
            logger.info("Usuário autenticado: {}", usuario.getName());


            var token = tokenService.generateToken(usuario);
            logger.info("Token JWT gerado com sucesso");


            if(user.getCompany() == null) {
                companyService.joinCompanyWithDomain(user);
            }
            logger.info("aqui!!!!!");
            //logger.info("Usuario: {}", user.toString());

            CompanyDTO companyDTO = null;

            if (user.getCompany() != null) {
                companyDTO = new CompanyDTO(
                        user.getCompany().getId(),
                        user.getCompany().getCnpj(),
                        user.getCompany().getName(),
                        user.getCompany().getDomains(),
                        user.getEmail()
                );
            }

            return ResponseEntity.ok(new LoginResponseDTO(
                    token,
                    user.getName(),
                    user.getEmail(),
                    user.getCpf(),
                    user.getRole(),
                    user.isEmailVerificado(),
                    companyDTO
            ));
        } catch (Exception e) {
            logger.error("Erro durante o login: {}", e.getMessage(), e);
            // Retornando erro com mensagem explicativa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO("Erro durante o login: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Validated RegisterDTO registerDTO) {

        logger.info("Iniciando registro");
        logger.info("Dados recebidos: {}", registerDTO);


        if(this.usuarioRepository.findByEmail(registerDTO.email()).isPresent()) return ResponseEntity.badRequest().build();

        logger.info("Dados não registrados");
        String encryptPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        logger.info("encryptPassword ");
        User user = new User(
                registerDTO.name(),
                registerDTO.email(),
                registerDTO.cpf(),
                encryptPassword,

                UserRole.USER

        );
        logger.info("user: {} ", user);


        if(usuarioRepository != null){
            logger.info("saving...");
            usuarioRepository.save(user);
            logger.info("saved!!!");

            String token = emailService.gerarToken(user.getEmail());
            String link = "http://localhost:8080/auth/checkEmail?token=" + token;
            logger.info("link: {} e email: {}", link, user.getEmail());
            emailService.enviarEmailConfirmacao(user.getEmail(), link);

            return ResponseEntity.ok(user);
        }

        logger.info(" usuarioRepository is nullo");
        return ResponseEntity.badRequest().build();


    }

    @GetMapping("/checkEmail")
    public ResponseEntity<String> confirmarEmail(@RequestParam String token) {
        try {
            logger.info("checkEmail()");
            String email = emailService.validarToken(token);

            User user = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário not fould"));

            if (user.isEmailVerificado()) {
                return ResponseEntity.ok("E-mail já foi confirmado.");
            }

            user.setEmailVerificado(true);
            usuarioRepository.save(user);
            logger.info("validado email");
            return ResponseEntity.ok("E-mail confirmado com sucesso!");
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token expirado.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido.");
        }
    }



}