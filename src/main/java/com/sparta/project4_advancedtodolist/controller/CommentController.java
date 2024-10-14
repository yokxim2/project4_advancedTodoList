package com.sparta.project4_advancedtodolist.controller;

import com.sparta.project4_advancedtodolist.dto.CommentRequestDto;
import com.sparta.project4_advancedtodolist.dto.CommentResponseDto;
import com.sparta.project4_advancedtodolist.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentResponseDto addComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.addComment(requestDto);
    }

    @GetMapping("/comments")
    public List<CommentResponseDto> getComments() {
        return commentService.getComments();
    }

    @PutMapping("/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
