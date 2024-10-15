package com.sparta.project4_advancedtodolist.service;

import com.sparta.project4_advancedtodolist.dto.TodoRequestDto;
import com.sparta.project4_advancedtodolist.dto.TodoResponseDto;
import com.sparta.project4_advancedtodolist.entity.Todo;
import com.sparta.project4_advancedtodolist.repository.TodoRepository;
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

    public TodoResponseDto createTodo(TodoRequestDto requestDto) {
        Todo todo = todoRepository.save(
                new Todo(requestDto.getTitle(),
                        requestDto.getContent()
                )
        );
        return new TodoResponseDto(todo);
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
