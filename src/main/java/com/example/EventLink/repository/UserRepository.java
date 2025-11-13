package com.example.EventLink.repository;

import com.example.EventLink.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUserName(String userName);
    Optional<UserEntity> findByUserEmail(String email);
}

