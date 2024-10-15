package com.sparta.project4_advancedtodolist.service;

import com.sparta.project4_advancedtodolist.dto.CommentRequestDto;
import com.sparta.project4_advancedtodolist.dto.CommentResponseDto;
import com.sparta.project4_advancedtodolist.entity.Comment;
import com.sparta.project4_advancedtodolist.entity.Todo;
import com.sparta.project4_advancedtodolist.entity.User;
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
    private final TodoService todoService;
    private final UserService userService;

    public CommentResponseDto addComment(Long todoId, CommentRequestDto requestDto) {
        Todo existTodo = todoService.findTodoById(todoId);
        User existUser = userService.findUserById(requestDto.getUserId());

        Comment comment = Comment.makeComment(existTodo, existUser, requestDto.getContent());

        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long todoId) {
        List<Comment> comments = todoService.findTodoById(todoId).getCommentList();
        List<CommentResponseDto> responseDto = new ArrayList<>();
        for (Comment comment : comments) {
            responseDto.add(new CommentResponseDto(comment));
        }
        return responseDto;
    }

    @Transactional
    public CommentResponseDto updateComment(Long todoId, Long commentId, CommentRequestDto requestDto) {
        Todo existTodo = todoService.findTodoById(todoId);
        Comment comment = findCommentById(commentId);
        if (!checkIfTodoMatches(existTodo, comment)) {
            throw new IllegalArgumentException("올바른 일정 ID를 입력해 주셔야 합니다.");
        }
        comment.update(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.delete(findCommentById(id));
    }

    // ==== 편의 메서드 ====

    @Transactional
    public Comment findCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );

        return comment;
    }

    private boolean checkIfTodoMatches(Todo existTodo, Comment comment) {
        return (existTodo == comment.getTodo());
    }
}
