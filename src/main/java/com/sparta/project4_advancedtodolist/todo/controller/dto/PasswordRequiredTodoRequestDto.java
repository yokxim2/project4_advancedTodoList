package com.sparta.project4_advancedtodolist.todo.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequiredTodoRequestDto {
    @NotNull(message = "비밀번호를 알맞게 입력해 주세요")
    private String password;

    @NotNull(message = "회원 ID값은 null일 수 없습니다.")
    private Long userId;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;
}
