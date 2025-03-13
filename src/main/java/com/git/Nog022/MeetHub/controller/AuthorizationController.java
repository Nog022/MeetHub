package com.git.Nog022.MeetHub.controller;

import com.git.Nog022.MeetHub.config.TokenService;
import com.git.Nog022.MeetHub.dto.AuthorizationDTO;
import com.git.Nog022.MeetHub.dto.LoginResponseDTO;
import com.git.Nog022.MeetHub.dto.RegisterDTO;
import com.git.Nog022.MeetHub.entity.User;
import com.git.Nog022.MeetHub.enums.UserRole;
import com.git.Nog022.MeetHub.repository.UserRepository;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthorizationDTO data) {
        try {
            logger.info("Iniciando login");
            logger.info("Dados recebidos: Email: {}, Senha: {}", data.email(), data.password());

            // Criando o token de autenticação com CPF e senha
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            logger.info("Token de autenticação gerado: {}", usernamePassword);

            // Autenticando o usuário
            var auth = this.authenticationManager.authenticate(usernamePassword);
            logger.info("Autenticação bem-sucedida");

            // Recuperando o usuário autenticado
            var usuario = (User) auth.getPrincipal();
            logger.info("Usuário autenticado: {}", usuario.getName());

            // Gerando o token JWT para o usuário autenticado
            var token = tokenService.generateToken(usuario);
            logger.info("Token JWT gerado com sucesso");

            // Retornando a resposta com o token
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (Exception e) {
            logger.error("Erro durante o login: {}", e.getMessage(), e);
            // Retornando erro com mensagem explicativa
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginResponseDTO("Erro durante o login: " + e.getMessage()));
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
        User usuario = new User(
                registerDTO.name(),
                registerDTO.email(),
                registerDTO.cpf(),
                encryptPassword,
                registerDTO.companyName(),

                UserRole.USER

        );
        logger.info("user: {} ", usuario);


        if(usuarioRepository != null){
            logger.info(" usuarioRepository existe");
            return ResponseEntity.ok(usuarioRepository.save(usuario));
        }
        logger.info(" usuarioRepository é nullo");
        return ResponseEntity.badRequest().build();


    }


}