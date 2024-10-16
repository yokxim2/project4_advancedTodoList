package com.sparta.project4_advancedtodolist.domain.entity.todo;

import com.sparta.project4_advancedtodolist.domain.entity.TimeStamped;
import com.sparta.project4_advancedtodolist.domain.entity.comment.Comment;
import com.sparta.project4_advancedtodolist.domain.entity.usertodo.UserTodo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Table(name = "todos")
@NoArgsConstructor
public class Todo extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL)
    List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    List<UserTodo> userTodoList = new ArrayList<>();

    private Todo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Todo makeTodo(String title, String content) {
        return new Todo(title, content);
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return Objects.equals(getId(), todo.getId()) && Objects.equals(getTitle(), todo.getTitle()) && Objects.equals(getContent(), todo.getContent()) && Objects.equals(getCommentList(), todo.getCommentList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getContent(), getCommentList());
    }
}
