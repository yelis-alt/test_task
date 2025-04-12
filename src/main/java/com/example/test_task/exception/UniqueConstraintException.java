package com.example.test_task.exception;


import javax.validation.ValidationException;

public class UniqueConstraintException extends ValidationException {
    public UniqueConstraintException(String message) {
        super(message);
    }
}