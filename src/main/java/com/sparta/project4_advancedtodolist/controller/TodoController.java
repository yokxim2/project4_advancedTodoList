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

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public TodoResponseDto create(@RequestBody TodoRequestDto requestDto) {
        return todoService.create(requestDto);
    }

    @GetMapping("/todos")
    public Page<TodoResponseDto> get(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {
        return todoService.get(page - 1, size, sortBy, isAsc);
    }

    @PutMapping("/todos/{todoId}")
    public TodoResponseDto update(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        return todoService.update(id, requestDto);
    }

    @DeleteMapping("/todos/{todoId}")
    public void delete(@PathVariable Long id) {
        todoService.delete(id);
    }
}
