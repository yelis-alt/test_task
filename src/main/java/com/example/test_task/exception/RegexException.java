package com.example.test_task.exception;

import jakarta.validation.ValidationException;

public class RegexException extends ValidationException {
    public RegexException(String message) {
        super(message);
    }
}
