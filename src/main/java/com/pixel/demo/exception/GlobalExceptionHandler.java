package com.pixel.demo.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null) ip = request.getRemoteAddr();

            String method = request.getMethod();
            String uri = request.getRequestURI();

            log.info("Request: {} {} from IP: {}", method, uri, ip);
        }
        return true;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(EntityNotFoundException ex) {
        log.warn("Entity not found", ex);
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, List.of());
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

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handle(IllegalArgumentException ex) {
        log.warn("Invalid input", ex);
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
