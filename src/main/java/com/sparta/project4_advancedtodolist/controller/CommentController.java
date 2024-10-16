package com.sparta.project4_advancedtodolist.controller;

import com.sparta.project4_advancedtodolist.dto.comment.CommentRequestDto;
import com.sparta.project4_advancedtodolist.dto.comment.CommentResponseDto;
import com.sparta.project4_advancedtodolist.dto.comment.PasswordRequiredCommentRequestDto;
import com.sparta.project4_advancedtodolist.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos/{todoId}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentResponseDto addComment(
            @PathVariable Long todoId,
            @Valid @RequestBody CommentRequestDto requestDto
    ) {
        return commentService.addComment(todoId, requestDto);
    }

    @GetMapping("/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long todoId) {
        return commentService.getComments(todoId);
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponseDto updateComment(
            @PathVariable Long todoId,
            @PathVariable Long commentId,
            @Valid @RequestBody PasswordRequiredCommentRequestDto requestDto
    ) {
        return commentService.updateComment(todoId, commentId, requestDto);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(
            @PathVariable Long commentId,
            @Valid @RequestBody PasswordRequiredCommentRequestDto requestDto
    ) {
        commentService.deleteComment(commentId, requestDto);
    }
}
