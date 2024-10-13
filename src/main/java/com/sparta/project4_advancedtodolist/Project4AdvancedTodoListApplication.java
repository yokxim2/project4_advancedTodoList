package com.sparta.project4_advancedtodolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Project4AdvancedTodoListApplication {

    public static void main(String[] args) {
        SpringApplication.run(Project4AdvancedTodoListApplication.class, args);
    }

}
