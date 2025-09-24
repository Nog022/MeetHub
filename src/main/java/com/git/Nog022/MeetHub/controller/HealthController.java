package com.git.Nog022.MeetHub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }


    private final JdbcTemplate jdbcTemplate;

    public HealthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/ping-db")
    public ResponseEntity<String> pingDb() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return ResponseEntity.ok("DB is alive");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("DB error: " + e.getMessage());
        }
    }
}
