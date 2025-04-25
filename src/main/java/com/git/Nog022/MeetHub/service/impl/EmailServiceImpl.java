package com.git.Nog022.MeetHub.service.impl;

import com.git.Nog022.MeetHub.config.SecurityFilter;
import com.git.Nog022.MeetHub.service.EmailService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static java.security.KeyRep.Type.SECRET;


@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    public static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;
    //TODO mudar a conforma de passagem da chave para o token
    private final Key key = Keys.hmacShaKeyFor("sua-chave-secreta-com-mais-de-32-caracteres!".getBytes());

    @Override
    public void enviarEmailConfirmacao(String destinatario, String link) {
        logger.info("enviarEmailConfirmacao()");



        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject("Confirme seu e-mail - MeetHub");
        mensagem.setText("Clique no link para confirmar seu e-mail:\n" + link);
        logger.info("send confirmation email");
        mailSender.send(mensagem);
    }



    public String gerarToken(String email) {
        logger.info("Gerando token...");

        try {
            String token = Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(new Date())
                    .setExpiration(Date.from(Instant.now().plus(3, ChronoUnit.DAYS)))  //disponivel por 1 dia
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();

            logger.info("Token gerado com sucesso: {}", token);
            return token;

        } catch (Exception e) {
            logger.error("Erro ao gerar token: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao gerar token", e);
        }
    }

    public String validarToken(String token) {
        logger.info("Validando token...");

        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

        } catch (Exception e) {
            logger.error("Token inválido: {}", e.getMessage(), e);
            throw new RuntimeException("Token inválido ou expirado", e);
        }
    }
}
