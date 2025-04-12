package com.example.test_task.exception;


import javax.validation.ValidationException;

public class InsufficientFundsException extends ValidationException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}