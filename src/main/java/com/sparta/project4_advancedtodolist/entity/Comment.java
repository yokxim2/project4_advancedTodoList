package com.sparta.project4_advancedtodolist.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comments")
@NoArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id")
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private Comment(Todo todo, User user, String content) {
        this.todo = todo;
        this.user = user;
        this.content = content;
    }

    public static Comment makeComment(String content, Todo todo, User user) {
        return new Comment(todo, user, content);
    }

    public void update(String content) {
        this.content = content;

    }
}
