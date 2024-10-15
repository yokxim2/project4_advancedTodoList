package com.sparta.project4_advancedtodolist.repository;

import com.sparta.project4_advancedtodolist.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
