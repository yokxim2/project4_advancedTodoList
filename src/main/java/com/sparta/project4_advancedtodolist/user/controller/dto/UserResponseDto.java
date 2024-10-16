package com.sparta.project4_advancedtodolist.user.controller.dto;

import com.sparta.project4_advancedtodolist.domain.entity.user.User;
import com.sparta.project4_advancedtodolist.domain.entity.user.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private UserRole role;
    private String createdAt;
    private String modifiedAt;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.createdAt = user.getCreatedAt().toString();
        this.modifiedAt = user.getModifiedAt().toString();
    }
}
