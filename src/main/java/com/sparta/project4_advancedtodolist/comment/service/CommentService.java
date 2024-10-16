package com.sparta.project4_advancedtodolist.comment.service;

import com.sparta.project4_advancedtodolist.comment.controller.dto.CommentRequestDto;
import com.sparta.project4_advancedtodolist.comment.controller.dto.CommentResponseDto;
import com.sparta.project4_advancedtodolist.comment.controller.dto.PasswordRequiredCommentRequestDto;
import com.sparta.project4_advancedtodolist.domain.entity.comment.Comment;
import com.sparta.project4_advancedtodolist.domain.entity.todo.Todo;
import com.sparta.project4_advancedtodolist.domain.entity.user.User;
import com.sparta.project4_advancedtodolist.exception.DataNotFoundException;
import com.sparta.project4_advancedtodolist.exception.PasswordUnmatchException;
import com.sparta.project4_advancedtodolist.domain.entity.comment.CommentRepository;
import com.sparta.project4_advancedtodolist.todo.service.TodoService;
import com.sparta.project4_advancedtodolist.user.service.UserService;
import jakarta.validation.Valid;
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
        commentRepository.save(comment);

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
    public CommentResponseDto updateComment(Long todoId, Long commentId, @Valid PasswordRequiredCommentRequestDto requestDto) {
        Todo existTodo = todoService.findTodoById(todoId);
        Comment comment = findCommentById(commentId);
        checkIfTodoMatches(existTodo, comment);
        checkPassword(comment, requestDto.getPreviousPassword());
        comment.update(requestDto.getContent());
        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long todoId, Long commentId, @Valid PasswordRequiredCommentRequestDto requestDto) {
        Todo existTodo = todoService.findTodoById(todoId);
        Comment comment = findCommentById(commentId);
        checkIfTodoMatches(existTodo, comment);
        checkPassword(comment, requestDto.getPreviousPassword());
        commentRepository.delete(findCommentById(commentId));
    }


    // ==== 편의 메서드 ====
    @Transactional
    public void checkPassword(Comment comment, String previousPassword) {
        if (!comment.getUser().getPassword().equals(previousPassword)) {
            throw new PasswordUnmatchException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public Comment findCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("해당하는 댓글이 존재하지 않습니다.")
        );

        return comment;
    }

    private void checkIfTodoMatches(Todo existTodo, Comment comment) {
        if (!(existTodo == comment.getTodo())) {
            throw new DataNotFoundException("해당하는 일정이 존재하지 않습니다.");
        }
    }
}
