package com.sparta.project4_advancedtodolist.service;

import com.sparta.project4_advancedtodolist.dto.CommentRequestDto;
import com.sparta.project4_advancedtodolist.dto.CommentResponseDto;
import com.sparta.project4_advancedtodolist.entity.Comment;
import com.sparta.project4_advancedtodolist.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponseDto addComment(CommentRequestDto requestDto) {
        Comment comment = commentRepository.save(
                new Comment(requestDto.getContent())
        );
        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponseDto> responseDto = new ArrayList<>();
        for (Comment comment : comments) {
            responseDto.add(new CommentResponseDto(comment));
        }
        return responseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = findCommentById(id);
        comment.update(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = findCommentById(id);
        commentRepository.delete(comment);
    }

    @Transactional
    public Comment findCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );

        return comment;
    }
}
