package com.example.test_task.exception;

import jakarta.validation.ValidationException;

public class UniqueConstraintException extends ValidationException {
    public UniqueConstraintException(String message) {
        super(message);
    }
}