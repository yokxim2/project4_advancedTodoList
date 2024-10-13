package com.sparta.project4_advancedtodolist.controller;

import com.sparta.project4_advancedtodolist.dto.SignupRequestDto;
import com.sparta.project4_advancedtodolist.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public void signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError: bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
        }

        userService.signup(requestDto);
    }
}
