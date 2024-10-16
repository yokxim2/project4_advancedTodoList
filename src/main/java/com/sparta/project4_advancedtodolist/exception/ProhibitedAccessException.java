package com.sparta.project4_advancedtodolist.exception;

public class ProhibitedAccessException extends RuntimeException {
    public ProhibitedAccessException(String message) {
        super(message);
    }
}
