package com.sparta.project4_advancedtodolist.dto.todo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ManagerRequestDto {
    @NotBlank(message = "일정 생성자의 비밀번호를 알맞게 입력해 주십시오.")
    private String password;

    @NotBlank(message = "유저명은 필수 항목입니다.")
    private String username;
}
