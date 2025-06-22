package com.pixel.demo.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(
        HttpStatus status,
        String message,
        List<String> details
) {}
