package com.example.EventLink.controller;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-db")
@ConditionalOnProperty(name = "spring.datasource.url")
public class DbTestController {

    private final JdbcTemplate jdbcTemplate;

    public DbTestController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public String testConnection() {
        try {
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return "✅ DB connection OK! Result: " + result;
        } catch (Exception e) {
            return "❌ DB connection FAILED: " + e.getMessage();
        }
    }
}
