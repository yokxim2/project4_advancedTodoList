package com.sparta.project4_advancedtodolist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ManagerRequestDto {
    @NotBlank(message = "유저명은 필수 항목입니다.")
    private String username;
}
