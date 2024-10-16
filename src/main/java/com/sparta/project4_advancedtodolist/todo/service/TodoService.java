package com.sparta.project4_advancedtodolist.todo.service;

import com.sparta.project4_advancedtodolist.user.service.UserService;
import com.sparta.project4_advancedtodolist.todo.controller.dto.ManagerRequestDto;
import com.sparta.project4_advancedtodolist.todo.controller.dto.PasswordRequiredTodoRequestDto;
import com.sparta.project4_advancedtodolist.todo.controller.dto.TodoRequestDto;
import com.sparta.project4_advancedtodolist.todo.controller.dto.TodoResponseDto;
import com.sparta.project4_advancedtodolist.domain.entity.todo.Todo;
import com.sparta.project4_advancedtodolist.domain.entity.user.User;
import com.sparta.project4_advancedtodolist.domain.entity.usertodo.UserTodo;
import com.sparta.project4_advancedtodolist.exception.DataNotFoundException;
import com.sparta.project4_advancedtodolist.exception.PasswordUnmatchException;
import com.sparta.project4_advancedtodolist.exception.ProhibitedAccessException;
import com.sparta.project4_advancedtodolist.domain.entity.todo.TodoRepository;
import com.sparta.project4_advancedtodolist.domain.entity.usertodo.UserTodoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserService userService;
    private final UserTodoRepository userTodoRepository;

    @Transactional
    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        User existUser = userService.findUserById(requestDto.getUserId());

        Todo todo = todoRepository.save(
                Todo.makeTodo(
                        requestDto.getTitle(),
                        requestDto.getContent()
                )
        );

        userTodoRepository.save(UserTodo.makeUserTodo(existUser,todo));

        return new TodoResponseDto(todo);
    }

    @Transactional
    public void addManager(Long todoId, ManagerRequestDto requestDto) {
        Todo existTodo = findTodoById(todoId);
        checkCreatorPassword(existTodo, requestDto.getPassword());
        User existUser = userService.findUserByUsername(requestDto.getUsername());

        userTodoRepository.save(UserTodo.makeUserTodo(existUser, existTodo));
    }

    @Transactional(readOnly = true)
    public Page<TodoResponseDto> getTodos(int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return todoRepository.findAll(pageable).map(TodoResponseDto::new);
    }

    @Transactional
    public TodoResponseDto updateTodo(Long id, @Valid PasswordRequiredTodoRequestDto requestDto) {
        Todo existTodo = findTodoById(id);
        User editor = userService.findUserById(requestDto.getUserId());

        checkEditorPermission(existTodo, editor, requestDto.getPassword());

        existTodo.update(requestDto.getTitle(), requestDto.getContent());
        return new TodoResponseDto(existTodo);
    }

    @Transactional
    public void deleteTodo(Long id, @Valid PasswordRequiredTodoRequestDto requestDto) {
        Todo existTodo = findTodoById(id);
        checkCreatorPassword(existTodo, requestDto.getPassword());

        todoRepository.delete(existTodo);
    }

    // ==== 편의 메서드 ====

    @Transactional
    // 일정을 삭제하려고 할 때, 혹은 일정 수정 권한을 공유 기능은 일정 작성자의 권한이다.
    public void checkCreatorPassword(Todo existTodo, String previousPassword) {
        UserTodo matchedUserTodo = existTodo.getUserTodoList().stream()
                .filter(userTodo -> userTodo.getTodo().equals(existTodo))
                .findFirst()
                .orElseThrow(
                        () -> new DataNotFoundException("해당하는 유저가 존재하지 않습니다.")
                );

        User user = matchedUserTodo.getUser();
        if (!user.getPassword().equals(previousPassword)) {
            throw new PasswordUnmatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    // 일정을 수정하려고 할 때 수정 권한이 있는지 확인 후 비밀번호를 검증한다.
    public void checkEditorPermission(Todo existTodo, User editor, String password) {
        List<User> users = existTodo.getUserTodoList().stream()
                .map(UserTodo::getUser)
                .toList();

        if (!users.contains(editor)) {
            throw new ProhibitedAccessException("해당 일정에 대한 수정 권한이 없습니다.");
        }

        if (!editor.getPassword().equals(password)) {
            throw new PasswordUnmatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public Todo findTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("해당 일정이 존재하지 않습니다.")
        );
        return todo;
    }
}
