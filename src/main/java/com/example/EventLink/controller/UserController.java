package com.example.EventLink.controller;

import com.example.EventLink.entity.UserEntity;
import com.example.EventLink.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.oauth2.core.user.OAuth2User;


import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

@GetMapping("/profile")
public ResponseEntity<UserEntity> getUserProfile(@AuthenticationPrincipal OAuth2User oauthUser) {
    if (oauthUser == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String email = oauthUser.getAttribute("email");
    if (email == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    UserEntity user = userRepository.findByUserEmail(email)
            .orElseGet(() -> {
                // Create new user if not found
                UserEntity newUser = new UserEntity();
                newUser.setUserEmail(email);
                newUser.setUserName(oauthUser.getAttribute("name")); // or "given_name"
                newUser.setProfilePicture(oauthUser.getAttribute("picture"));
                return userRepository.save(newUser);
            });

    return ResponseEntity.ok(user);
}

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        return ResponseEntity.ok(users); // 200 OK
    }
}
