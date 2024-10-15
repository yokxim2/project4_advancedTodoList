package com.sparta.project4_advancedtodolist.repository;

import com.sparta.project4_advancedtodolist.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Long countAllById(Long todoId);
}
