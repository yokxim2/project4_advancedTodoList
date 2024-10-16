package com.sparta.project4_advancedtodolist.domain.entity.usertodo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTodoRepository extends JpaRepository<UserTodo, Long> {
}
