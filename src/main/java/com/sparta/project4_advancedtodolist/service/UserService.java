package com.sparta.project4_advancedtodolist.service;

import com.sparta.project4_advancedtodolist.dto.user.PasswordRequiredUserRequestDto;
import com.sparta.project4_advancedtodolist.dto.user.SignupRequestDto;
import com.sparta.project4_advancedtodolist.dto.user.UserResponseDto;
import com.sparta.project4_advancedtodolist.entity.User;
import com.sparta.project4_advancedtodolist.entity.UserRole;
import com.sparta.project4_advancedtodolist.exception.DataNotFoundException;
import com.sparta.project4_advancedtodolist.exception.PasswordUnmatchException;
import com.sparta.project4_advancedtodolist.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public UserResponseDto updateUser(Long userId, @Valid PasswordRequiredUserRequestDto requestDto) {
        User user = findUserById(userId);
        checkPassword(user, requestDto.getPreviousPassword());
        user.update(requestDto.getUsername(), requestDto.getPassword(), requestDto.getEmail());
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long id, @Valid PasswordRequiredUserRequestDto requestDto) {
        User user = findUserById(id);
        checkPassword(user, requestDto.getPreviousPassword());
        userRepository.delete(user);
    }

    // ==== 편의 메서드 ====
    @Transactional
    public void checkPassword(User user, @NotBlank(message = "이전 비밀번호를 알맞게 입력해 주세요.") String previousPassword) {
        if (!user.getPassword().equals(previousPassword)) {
            throw new PasswordUnmatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public User findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("해당하는 유저가 존재하지 않습니다.")
        );
        return user;
    }

    @Transactional
    public User findUserByUsername(String username) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(
                () -> new DataNotFoundException("해당하는 유저가 존재하지 않습니다.")
        ));
        return user.get();
    }
}
