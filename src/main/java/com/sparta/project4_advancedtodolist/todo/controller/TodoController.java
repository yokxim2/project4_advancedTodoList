package com.sparta.project4_advancedtodolist.todo.controller;

import com.sparta.project4_advancedtodolist.todo.controller.dto.ManagerRequestDto;
import com.sparta.project4_advancedtodolist.todo.controller.dto.PasswordRequiredTodoRequestDto;
import com.sparta.project4_advancedtodolist.todo.controller.dto.TodoRequestDto;
import com.sparta.project4_advancedtodolist.todo.controller.dto.TodoResponseDto;
import com.sparta.project4_advancedtodolist.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public TodoResponseDto createTodo(@Valid @RequestBody TodoRequestDto requestDto) {
        return todoService.createTodo(requestDto);
    }

    @PostMapping("/todos/{todoId}/add-manager")
    public void addManager(
            @PathVariable Long todoId,
            @Valid @RequestBody ManagerRequestDto requestDto
    ) {
        todoService.addManager(todoId, requestDto);
    }

    @GetMapping("/todos")
    public Page<TodoResponseDto> getTodos(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "modifiedAt") String sortBy,
            @RequestParam(value = "isAsc", defaultValue = "false") boolean isAsc
    ) {
        return todoService.getTodos(page - 1, size, sortBy, isAsc);
    }

    @PutMapping("/todos/{todoId}")
    public TodoResponseDto updateTodo(
            @PathVariable Long todoId,
            @Valid @RequestBody PasswordRequiredTodoRequestDto requestDto
    ) {
        return todoService.updateTodo(todoId, requestDto);
    }

    @DeleteMapping("/todos/{todoId}")
    public void deleteTodo(
            @PathVariable Long todoId,
            @Valid @RequestBody PasswordRequiredTodoRequestDto requestDto
    ) {
        todoService.deleteTodo(todoId, requestDto);
    }
}
