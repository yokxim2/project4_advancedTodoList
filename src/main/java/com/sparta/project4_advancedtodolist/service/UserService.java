package com.sparta.project4_advancedtodolist.service;

import com.sparta.project4_advancedtodolist.dto.SignupRequestDto;
import com.sparta.project4_advancedtodolist.dto.UserRequestDto;
import com.sparta.project4_advancedtodolist.dto.UserResponseDto;
import com.sparta.project4_advancedtodolist.entity.User;
import com.sparta.project4_advancedtodolist.entity.UserRole;
import com.sparta.project4_advancedtodolist.repository.CommentRepository;
import com.sparta.project4_advancedtodolist.repository.TodoRepository;
import com.sparta.project4_advancedtodolist.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto signup(@Valid SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();
        UserRole role = UserRole.USER;

        Boolean existDuplicatedUsername = userRepository.countAllByUsername(username) > 0;
        if (existDuplicatedUsername) {
            throw new IllegalArgumentException("중복된 회원명이 존재합니다.");
        }

        Boolean existDuplicateEmail = userRepository.countAllByEmail(email) > 0;
        if (existDuplicateEmail) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }

        if (requestDto.isAdmin()) {
            // Admin 토큰 비교 로직
            // Admin signup은 따로 구현해야 한다.
            role = UserRole.ADMIN;
        }

        User user = User.signup(username, password, email);
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users) {
            userResponseDtos.add(new UserResponseDto(user));
        }
        return userResponseDtos;
    }

    @Transactional
    public UserResponseDto updateUser(Long userId, @Valid UserRequestDto requestDto) {
        User user = findUserById(userId);
        user.update(requestDto.getUsername(), requestDto.getPassword(), requestDto.getEmail());
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }
    // ==== 편의 메서드 ====

    @Transactional
    public User findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );
        return user;
    }
}
