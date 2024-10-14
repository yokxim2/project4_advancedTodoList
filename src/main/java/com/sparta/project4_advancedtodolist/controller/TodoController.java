package com.sparta.project4_advancedtodolist.controller;

import com.sparta.project4_advancedtodolist.dto.TodoRequestDto;
import com.sparta.project4_advancedtodolist.dto.TodoResponseDto;
import com.sparta.project4_advancedtodolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public TodoResponseDto createTodo(@RequestBody TodoRequestDto requestDto) {
        return todoService.createTodo(requestDto);
    }

    @GetMapping("/todos")
    public Page<TodoResponseDto> getTodos(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {
        return todoService.getTodos(page - 1, size, sortBy, isAsc);
    }

    @PutMapping("/todos/{todoId}")
    public TodoResponseDto updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        return todoService.updateTodo(id, requestDto);
    }

    @DeleteMapping("/todos/{todoId}")
    public
}
