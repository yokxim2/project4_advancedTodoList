package com.sparta.project4_advancedtodolist.domain.entity.comment;

import com.sparta.project4_advancedtodolist.domain.entity.TimeStamped;
import com.sparta.project4_advancedtodolist.domain.entity.todo.Todo;
import com.sparta.project4_advancedtodolist.domain.entity.user.User;
import jakarta.persistence.*;
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

    public static Comment makeComment(Todo todo, User user, String content) {
        return new Comment(todo, user, content);
    }

    public void update(String content) {
        this.content = content;

    }
}
