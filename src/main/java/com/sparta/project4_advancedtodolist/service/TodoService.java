package com.sparta.project4_advancedtodolist.service;

import com.sparta.project4_advancedtodolist.dto.TodoRequestDto;
import com.sparta.project4_advancedtodolist.dto.TodoResponseDto;
import com.sparta.project4_advancedtodolist.entity.Todo;
import com.sparta.project4_advancedtodolist.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        Todo todo = todoRepository.save(
                new Todo(requestDto.getTitle(),
                        requestDto.getContent()
                )
        );
        return new TodoResponseDto(todo);
    }
}
