package com.sparta.project4_advancedtodolist.service;

import com.sparta.project4_advancedtodolist.dto.SignupRequestDto;
import com.sparta.project4_advancedtodolist.dto.UserResponseDto;
import com.sparta.project4_advancedtodolist.entity.*;
import com.sparta.project4_advancedtodolist.repository.CommentRepository;
import com.sparta.project4_advancedtodolist.repository.TodoRepository;
import com.sparta.project4_advancedtodolist.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    public UserResponseDto signup(@Valid SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String email = requestDto.getEmail();
        UserRole role = UserRole.USER;

        Optional<User> findByUsername = userRepository.findByUsername(username);
        if (findByUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 회원명이 존재합니다.");
        }

        Optional<User> findByEmail = userRepository.findByEmail(email);
        if (findByEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }

        if (requestDto.isAdmin()) {
            // Admin 토큰 비교 로직
            role = UserRole.ADMIN;
        }

        User user = new User(username, password, email, role);
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users) {
            userResponseDtos.add(new UserResponseDto(user));
        }
        return userResponseDtos;
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = findUserById(id);

        // 여기 어떻게 구현해야 하는가?
        // 유저 삭제 시 일정을 삭제해야 한다. 하지만 중간 테이블이 섞여 있어서, 이렇게 하는게 아닌거 같음
        List<UserTodo> userTodoList = user.getUserTodoList();
        for (UserTodo ut : userTodoList) {
            Todo todo = ut.getTodo();
            todoRepository.delete(todo);
        }

        // 만약 삭제한 유저가 만든 일정에 댓글을 달았었다면, 삭제가 두번 이루어진다.
        commentRepository.deleteAll(user.getCommentList());
    }

    // ==== 편의 메서드 ====
    @Transactional
    public User findUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 유저가 없습니다.")
        );
        return user;
    }
}
