package com.sparta.project4_advancedtodolist.service;

import com.sparta.project4_advancedtodolist.dto.SignupRequestDto;
import com.sparta.project4_advancedtodolist.entity.User;
import com.sparta.project4_advancedtodolist.entity.UserRole;
import com.sparta.project4_advancedtodolist.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void signup(@Valid SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
//        String password = requestDto.getPassword();
        String email = requestDto.getEmail();
        UserRole role = UserRole.USER;

        Optional<User> findByUsername = userRepository.findByUsername(username);
        if (findByUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 회원명이 존재합니다.");
        }

        Optional<User> findByEmail = userRepository.findByEmail(email);
        if (findByEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }

        if (requestDto.isAdmin()) {
            // Admin 토큰 비교 로직
            role = UserRole.ADMIN;
        }

        User user = new User(username, email, role);
        userRepository.save(user);
    }
}
