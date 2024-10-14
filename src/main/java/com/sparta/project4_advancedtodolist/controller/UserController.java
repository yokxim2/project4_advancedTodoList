package com.sparta.project4_advancedtodolist.controller;

import com.sparta.project4_advancedtodolist.dto.SignupRequestDto;
import com.sparta.project4_advancedtodolist.dto.UserResponseDto;
import com.sparta.project4_advancedtodolist.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/users/signup")
    public UserResponseDto signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError: bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
        }

        return userService.signup(requestDto);
    }

    @GetMapping("/users")
    public List<UserResponseDto> getUsers() {
        return userService.getUsers();
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
