package com.pixel.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmaiReqlDto(@Email(message = "email must be correct") @NotNull String oldEmail,
                          @Email(message = "email must be correct") @NotNull String email) {}
