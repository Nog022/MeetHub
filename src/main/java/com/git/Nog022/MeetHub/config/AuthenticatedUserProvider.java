package com.git.Nog022.MeetHub.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {

    @Autowired
    private TokenService tokenService;

    public Long getUserId(HttpServletRequest request) {
        String token = extractToken(request);
        return tokenService.extractUserId(token);
    }

    public String getRole(HttpServletRequest request) {
        String token = extractToken(request);
        return tokenService.extractRole(token);
    }

    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("not found token in header");
        }
        return authHeader.replace("Bearer ", "");
    }
}

