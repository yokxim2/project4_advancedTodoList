package com.sparta.project4_advancedtodolist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "todos")
public class Todo extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();
}
