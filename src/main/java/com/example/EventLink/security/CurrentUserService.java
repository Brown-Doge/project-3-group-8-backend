package com.example.EventLink.security;

import java.util.Optional;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.EventLink.entity.UserEntity;
import com.example.EventLink.repository.UserRepository;

/**
 * Maps the authenticated OAuth2 user -> your app's UserEntity (by email).
 * Creates the UserEntity on first login if it doesn't exist.
 */
@Service
public class CurrentUserService {

    private final UserRepository users;

    public CurrentUserService(UserRepository users) {
        this.users = users;
    }

    /**
     * Returns the application user id (Integer) for the logged-in principal.
     */
    @Transactional
    public Integer currentUserId(OAuth2User principal) {
        if (principal == null) {
            throw new IllegalStateException("No authenticated user");
        }

        // Common Google claims
        String email = (String) principal.getAttributes().get("email");
        String name  = (String) principal.getAttributes().getOrDefault("name", email);

        if (email == null || email.isBlank()) {
            throw new IllegalStateException("Authenticated user missing email claim");
        }

        // Find by email; create if missing
        Optional<UserEntity> existing = users.findByUserEmail(email);
        UserEntity user = existing.orElseGet(() -> {
            UserEntity u = new UserEntity();
            u.setUserEmail(email);
            u.setUserName(name != null ? name : email);
            // password/profile_picture can stay null/default for OAuth users
            return users.save(u);
        });

        // Your UserEntity likely uses Integer for user_id; convert if needed
        Long id = user.getUserId();
        return id == null ? null : Math.toIntExact(id);
    }
}
