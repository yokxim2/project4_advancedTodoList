package com.sparta.project4_advancedtodolist.service;

import com.sparta.project4_advancedtodolist.dto.CommentRequestDto;
import com.sparta.project4_advancedtodolist.dto.CommentResponseDto;
import com.sparta.project4_advancedtodolist.entity.Comment;
import com.sparta.project4_advancedtodolist.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponseDto create(CommentRequestDto requestDto) {
        Comment comment = commentRepository.save(
                new Comment(requestDto.getContent())
        );
        return new CommentResponseDto(comment);
    }
}
