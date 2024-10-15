package com.sparta.project4_advancedtodolist.repository;

import com.sparta.project4_advancedtodolist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Long countAllByUsername(String username);
    Long countAllByEmail(String email);
}
