package com.example.test_task.exception;


import javax.validation.ValidationException;

public class RegexException extends ValidationException {
    public RegexException(String message) {
        super(message);
    }
}
