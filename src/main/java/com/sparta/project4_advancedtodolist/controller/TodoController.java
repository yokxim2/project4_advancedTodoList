package com.sparta.project4_advancedtodolist.controller;

import com.sparta.project4_advancedtodolist.dto.ManagerRequestDto;
import com.sparta.project4_advancedtodolist.dto.TodoRequestDto;
import com.sparta.project4_advancedtodolist.dto.TodoResponseDto;
import com.sparta.project4_advancedtodolist.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public TodoResponseDto createTodo(@Valid TodoRequestDto requestDto) {
        return todoService.createTodo(requestDto);
    }

    @PostMapping("/todos/{todoId}/add-manager")
    public void addManager(
            @PathVariable Long todoId,
            @Valid ManagerRequestDto requestDto
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
            @Valid TodoRequestDto requestDto
    ) {
        return todoService.updateTodo(todoId, requestDto);
    }

    @DeleteMapping("/todos/{todoId}")
    public void deleteTodo(@PathVariable Long todoId) {
        todoService.deleteTodo(todoId);
    }
}
