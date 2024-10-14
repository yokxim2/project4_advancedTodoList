package com.sparta.project4_advancedtodolist.controller;

import com.sparta.project4_advancedtodolist.dto.CommentRequestDto;
import com.sparta.project4_advancedtodolist.dto.CommentResponseDto;
import com.sparta.project4_advancedtodolist.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentResponseDto create(@RequestBody CommentRequestDto requestDto) {
        return commentService.create(requestDto);
    }
}
