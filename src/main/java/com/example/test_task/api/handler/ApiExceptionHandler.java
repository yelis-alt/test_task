package com.example.test_task.api.handler;

import com.example.test_task.exception.CountConstraintException;
import com.example.test_task.exception.InsufficientFundsException;
import com.example.test_task.exception.RegexException;
import com.example.test_task.exception.UniqueConstraintException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(CountConstraintException.class)
    public ResponseEntity<?> handle(CountConstraintException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(RegexException.class)
    public ResponseEntity<?> handle(RegexException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<?> handle(InsufficientFundsException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(UniqueConstraintException.class)
    public ResponseEntity<?> handle(UniqueConstraintException exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
