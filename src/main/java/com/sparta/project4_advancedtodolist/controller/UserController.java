package com.sparta.project4_advancedtodolist.controller;

import com.sparta.project4_advancedtodolist.dto.user.PasswordRequiredUserRequestDto;
import com.sparta.project4_advancedtodolist.dto.user.SignupRequestDto;
import com.sparta.project4_advancedtodolist.dto.user.UserResponseDto;
import com.sparta.project4_advancedtodolist.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public UserResponseDto signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @GetMapping("/users")
    public List<UserResponseDto> getUsers() {
        return userService.getUsers();
    }

    @PutMapping("/users/{userId}")
    public UserResponseDto updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody PasswordRequiredUserRequestDto requestDto
    ) {
        return userService.updateUser(userId, requestDto);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(
            @PathVariable Long userId,
            @Valid @RequestBody PasswordRequiredUserRequestDto requestDto
    ) {
        userService.deleteUser(userId, requestDto);
    }


}
