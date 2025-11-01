package com.git.Nog022.MeetHub.service.impl;


import com.git.Nog022.MeetHub.service.EmailService;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sendgrid.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.sendgrid.helpers.mail.objects.Email;




@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    public static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    //TODO mudar a conforma de passagem da chave para o token
    private final Key key = Keys.hmacShaKeyFor("sua-chave-secreta-com-mais-de-32-caracteres!".getBytes());

    @Value("${SENDGRID_API_KEY}")
    private String sendgridApiKey;

    @Override
    public void enviarEmailConfirmacao(String destinatario, String link) {
        logger.info("enviarEmailConfirmacao()");

        Email from = new Email("meethub.contato@gmail.com");
        String subject = "Confirme seu e-mail - MeetHub";
        Email to = new Email(destinatario);
        Content content = new Content("text/plain", "Clique no link para confirmar seu e-mail:\n" + link);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendgridApiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            logger.info("Email enviado! Status: {}", response.getStatusCode());
        } catch (IOException ex) {
            logger.error("Erro ao enviar e-mail", ex);
        }
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
