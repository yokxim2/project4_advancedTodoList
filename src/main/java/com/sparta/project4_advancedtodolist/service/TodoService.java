package com.sparta.project4_advancedtodolist.service;

import com.sparta.project4_advancedtodolist.dto.ManagerRequestDto;
import com.sparta.project4_advancedtodolist.dto.TodoRequestDto;
import com.sparta.project4_advancedtodolist.dto.TodoResponseDto;
import com.sparta.project4_advancedtodolist.entity.Todo;
import com.sparta.project4_advancedtodolist.entity.User;
import com.sparta.project4_advancedtodolist.entity.UserTodo;
import com.sparta.project4_advancedtodolist.repository.TodoRepository;
import com.sparta.project4_advancedtodolist.repository.UserTodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto) {
        Todo existTodo = findTodoById(id);
        existTodo.update(requestDto.getTitle(), requestDto.getContent());
        return new TodoResponseDto(existTodo);
    }

    @Transactional
    public void deleteTodo(Long id) {
        Todo existTodo = findTodoById(id);
        todoRepository.delete(existTodo);
    }

    // ==== 편의 메서드 ====
    @Transactional
    public Todo findTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 일정이 존재하지 않습니다.")
        );
        return todo;
    }
}
