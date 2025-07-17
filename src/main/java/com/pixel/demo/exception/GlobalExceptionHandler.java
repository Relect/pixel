package com.pixel.demo.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler implements HandlerInterceptor {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(EntityNotFoundException ex) {
        log.warn("Entity not found", ex);
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, List.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex) {

        log.warn("MethodArgumentNotValidException", ex);
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> String.format("%s: %s",
                        error.getField(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());

        return buildResponse("Argument not valid", HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(LastPhoneDeletionException.class)
    public ResponseEntity<ErrorResponse> handle(LastPhoneDeletionException ex) {
        log.warn(ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, List.of());
    }

    @ExceptionHandler(LastEmailDeletionException.class)
    public ResponseEntity<ErrorResponse> handle(LastEmailDeletionException ex) {
        log.warn(ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, List.of());
    }

    @ExceptionHandler(NotEnoughMoney.class)
    public ResponseEntity<ErrorResponse> handle(NotEnoughMoney ex) {
        log.warn(ex.getMessage());
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, List.of());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handle(ConstraintViolationException ex) {

        List<String> errors = ex.getConstraintViolations().stream()
                .map(violation -> String.format("%s: %s",
                        violation.getPropertyPath(),
                        violation.getMessage()))
                .collect(Collectors.toList());

        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handle(IllegalArgumentException ex) {
        log.warn("Invalid input", ex);
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, List.of());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception ex) {
        log.warn("exception", ex);
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, List.of());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException ex) {
        Throwable rootCause = ex.getMostSpecificCause();
        log.warn("Error parsing JSON", rootCause);
        return buildResponse(rootCause.getMessage(), HttpStatus.BAD_REQUEST, List.of());
    }

    private ResponseEntity<ErrorResponse> buildResponse(String message, HttpStatus status, List<String> errors) {
        ErrorResponse error = new ErrorResponse(status, message, errors);
        return ResponseEntity.status(status).body(error);
    }
}
