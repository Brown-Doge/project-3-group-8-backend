package com.example.EventLink.controller;

import com.example.EventLink.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    /**
     * Generate JWT token after successful OAuth2 authentication
     */
    @PostMapping("/token")
    public ResponseEntity<Map<String, Object>> generateToken(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String username;
            
            // Handle OAuth2 authentication
            if (authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                username = oauth2User.getAttribute("email");
                if (username == null) {
                    username = oauth2User.getAttribute("login");
                }
                if (username == null) {
                    username = oauth2User.getName();
                }
            } else {
                username = authentication.getName();
            }

            // Generate JWT token
            String token = jwtService.generateToken(username);
            
            response.put("token", token);
            response.put("username", username);
            response.put("type", "Bearer");
            response.put("expiresIn", 86400); // 24 hours in seconds
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to generate token");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Generate a test JWT token (for development/testing only)
     */
    @PostMapping("/test-token")
    public ResponseEntity<Map<String, Object>> generateTestToken(@RequestParam(defaultValue = "test@example.com") String username) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Generate JWT token for test user
            String token = jwtService.generateToken(username);
            
            response.put("token", token);
            response.put("username", username);
            response.put("type", "Bearer");
            response.put("expiresIn", 86400); // 24 hours in seconds
            response.put("note", "This is a test token for development purposes");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to generate test token");
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Validate JWT token endpoint
     */
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.put("valid", false);
                response.put("error", "Invalid authorization header");
                return ResponseEntity.badRequest().body(response);
            }

            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            boolean isValid = jwtService.validateToken(token, username);
            
            response.put("valid", isValid);
            response.put("username", isValid ? username : null);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("valid", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Get current user info endpoint
     */
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getCurrentUser(Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        
        if (authentication == null) {
            response.put("error", "Not authenticated");
            return ResponseEntity.status(401).body(response);
        }

        try {
            if (authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                response.put("name", oauth2User.getAttribute("name"));
                response.put("email", oauth2User.getAttribute("email"));
                response.put("picture", oauth2User.getAttribute("picture"));
                response.put("provider", "google");
            } else {
                response.put("username", authentication.getName());
                response.put("authorities", authentication.getAuthorities());
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", "Failed to get user info");
            return ResponseEntity.badRequest().body(response);
        }
    }
}