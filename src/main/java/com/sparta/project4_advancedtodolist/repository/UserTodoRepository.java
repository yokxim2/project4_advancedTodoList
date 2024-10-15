package com.sparta.project4_advancedtodolist.repository;

import com.sparta.project4_advancedtodolist.entity.UserTodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTodoRepository extends JpaRepository<UserTodo, Long> {
}
