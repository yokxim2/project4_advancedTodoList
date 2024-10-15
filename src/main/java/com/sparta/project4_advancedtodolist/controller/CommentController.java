package com.sparta.project4_advancedtodolist.controller;

import com.sparta.project4_advancedtodolist.dto.CommentRequestDto;
import com.sparta.project4_advancedtodolist.dto.CommentResponseDto;
import com.sparta.project4_advancedtodolist.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/todos/{todoId}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentResponseDto addComment(@PathVariable Long todoId, @Valid CommentRequestDto requestDto) {
        return commentService.addComment(todoId, requestDto);
    }

    @GetMapping("/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long todoId) {
        return commentService.getComments(todoId);
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long todoId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(todoId, commentId, requestDto);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
