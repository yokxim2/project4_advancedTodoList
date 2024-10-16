package com.sparta.project4_advancedtodolist.dto.user;

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
