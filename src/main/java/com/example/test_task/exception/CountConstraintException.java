package com.example.test_task.exception;


import javax.validation.ValidationException;

public class CountConstraintException extends ValidationException {
    public CountConstraintException(String message) {
        super(message);
    }
}