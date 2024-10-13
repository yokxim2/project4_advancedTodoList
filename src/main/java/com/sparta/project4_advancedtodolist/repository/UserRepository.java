package com.sparta.project4_advancedtodolist.repository;

import com.sparta.project4_advancedtodolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}
