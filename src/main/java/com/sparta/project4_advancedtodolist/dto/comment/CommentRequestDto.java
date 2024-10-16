package com.sparta.project4_advancedtodolist.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    @NotNull(message = "회원 ID가 null 값이 될 수 없습니다.")
    private Long userId;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;
}
