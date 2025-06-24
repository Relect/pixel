package com.pixel.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record SearchUserReqDto(
    LocalDate dateOfBirth,
    @Pattern(
            regexp = "^7\\d{10}$",
            message = "Phone must be in format 79207865432")
    String phone,
    String name,
    @Email(message = "email must be correct")
    String email,
    int page,
    int size) {}
