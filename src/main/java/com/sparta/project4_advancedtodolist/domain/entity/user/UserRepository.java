package com.sparta.project4_advancedtodolist.domain.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Long countAllByUsername(String username);
    Long countAllByEmail(String email);

    Optional<User> findByUsername(String username);
}
