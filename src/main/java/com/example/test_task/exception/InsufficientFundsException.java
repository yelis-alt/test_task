package com.example.test_task.exception;

import jakarta.validation.ValidationException;

public class InsufficientFundsException extends ValidationException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}