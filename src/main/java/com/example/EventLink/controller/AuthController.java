package com.example.EventLink.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/success")
    public ResponseEntity<?> authSuccess(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of("error", "Authentication failed"));
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String picture = oAuth2User.getAttribute("picture");
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Authentication successful");
        response.put("authenticated", true);
        response.put("user", Map.of(
            "email", email != null ? email : "unknown",
            "name", name != null ? name : "unknown",
            "picture", picture != null ? picture : ""
        ));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/failure")
    public ResponseEntity<?> authFailure() {
        return ResponseEntity.status(401).body(Map.of(
            "error", "Authentication failed",
            "message", "OAuth2 authentication was not successful",
            "authenticated", false
        ));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body(Map.of(
                "error", "User not authenticated",
                "authenticated", false
            ));
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("email", oAuth2User.getAttribute("email"));
        userInfo.put("name", oAuth2User.getAttribute("name"));
        userInfo.put("picture", oAuth2User.getAttribute("picture"));
        userInfo.put("authenticated", true);
        userInfo.put("provider", "google");

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/logout-success")
    public ResponseEntity<?> logoutSuccess() {
        return ResponseEntity.ok(Map.of(
            "message", "Logout successful",
            "authenticated", false
        ));
    }
}