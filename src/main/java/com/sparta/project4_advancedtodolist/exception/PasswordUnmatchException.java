package com.sparta.project4_advancedtodolist.exception;

public class PasswordUnmatchException extends RuntimeException {
    public PasswordUnmatchException(String message) {
        super(message);
    }
}
