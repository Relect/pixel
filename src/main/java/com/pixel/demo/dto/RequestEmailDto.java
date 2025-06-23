package com.pixel.demo.dto;

import jakarta.validation.constraints.Email;

public record RequestEmailDto(@Email(message = "email must be correct") String oldEmail,
                              @Email(message = "email must be correct") String email) {}
