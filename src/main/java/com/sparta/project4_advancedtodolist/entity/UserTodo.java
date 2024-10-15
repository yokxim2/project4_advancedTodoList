package com.sparta.project4_advancedtodolist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user_todo")
@NoArgsConstructor
public class UserTodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    private UserTodo(User user, Todo todo) {
        this.user = user;
        this.todo = todo;
    }

    public static UserTodo makeUserTodo(User existUser, Todo todo) {
        return new UserTodo(existUser, todo);
    }
}
















