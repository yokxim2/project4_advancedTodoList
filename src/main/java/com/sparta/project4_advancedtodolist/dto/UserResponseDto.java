package com.sparta.project4_advancedtodolist.dto;

import com.sparta.project4_advancedtodolist.entity.User;
import com.sparta.project4_advancedtodolist.entity.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private UserRole role;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}
