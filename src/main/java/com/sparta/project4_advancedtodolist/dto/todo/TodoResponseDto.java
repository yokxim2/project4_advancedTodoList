package com.sparta.project4_advancedtodolist.dto.todo;

import com.sparta.project4_advancedtodolist.entity.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String title;
    private String content;
    private int commentCount;
    private String createdBy;
    private String createdAt;
    private String modifiedAt;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.commentCount = todo.getCommentList().size();
        this.createdBy = todo.getUserTodoList().stream()
                .filter(userTodo -> userTodo.getTodo().equals(todo))
                .findFirst()
                .map(userTodo -> userTodo.getUser().getUsername())
                .orElse("Unknown");
        this.createdAt = todo.getCreatedAt().toString();
        this.modifiedAt = todo.getModifiedAt().toString();
    }
}
