package com.git.Nog022.MeetHub.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface EmailService {

    void enviarEmailConfirmacao(String destinatario, String token);

    String gerarToken(String email);

    String validarToken(String token);
}
