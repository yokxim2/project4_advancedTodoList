package com.sparta.project4_advancedtodolist.service;

import com.sparta.project4_advancedtodolist.dto.comment.CommentRequestDto;
import com.sparta.project4_advancedtodolist.dto.comment.CommentResponseDto;
import com.sparta.project4_advancedtodolist.dto.comment.PasswordRequiredCommentRequestDto;
import com.sparta.project4_advancedtodolist.entity.Comment;
import com.sparta.project4_advancedtodolist.entity.Todo;
import com.sparta.project4_advancedtodolist.entity.User;
import com.sparta.project4_advancedtodolist.repository.CommentRepository;
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
    public void deleteComment(Long id, @Valid PasswordRequiredCommentRequestDto requestDto) {
        Comment comment = findCommentById(id);
        checkPassword(comment, requestDto.getPreviousPassword());
        commentRepository.delete(findCommentById(id));
    }


    // ==== 편의 메서드 ====
    @Transactional
    public void checkPassword(Comment comment, String previousPassword) {
        if (!comment.getUser().getPassword().equals(previousPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Transactional
    public Comment findCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );

        return comment;
    }

    private void checkIfTodoMatches(Todo existTodo, Comment comment) {
        if (!(existTodo == comment.getTodo())) {
            throw new IllegalArgumentException("일치하는 일정 ID 값이 아닙니다.");
        }
    }
}
