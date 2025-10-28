package com.example.event_link.controller;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @GetMapping("/success")
    public Map<String, Object> success(@AuthenticationPrincipal OAuth2User principal) {
        // Extract user info from OAuth2User
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");

        // Save or update user in DB here (e.g., Supabase/PostgreSQL)
        // Generate your own JWT or session token to return to frontend

        return Map.of(
                "message", "Login successful",
                "email", email,
                "name", name
        );
    }

    @GetMapping("/failure")
    public Map<String, String> failure() {
        return Map.of("error", "OAuth2 login failed");
    }

}
