package com.sparta.project4_advancedtodolist.domain.entity.user;

import com.sparta.project4_advancedtodolist.domain.entity.TimeStamped;
import com.sparta.project4_advancedtodolist.domain.entity.usertodo.UserTodo;
import com.sparta.project4_advancedtodolist.domain.entity.comment.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTodo> userTodoList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    private User(String username, String password, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public static User signup(String username, String password, String email) {
        return new User(username, password, email, UserRole.USER);
    }

    public void update(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
