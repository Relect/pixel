package com.pixel.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RequestPhoneDto (
    @Pattern(
            regexp = "^7\\d{10}$",
            message = "Телефон должен быть в формате  79207865432")
    @NotNull String oldPhone,
    @Pattern(
            regexp = "^7\\d{10}$",
            message = "Телефон должен быть в формате  79207865432")
    @NotNull String phone) {}
