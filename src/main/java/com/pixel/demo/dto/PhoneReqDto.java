package com.pixel.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record PhoneReqDto(
    @Pattern(
            regexp = "^7\\d{10}$",
            message = "Phone must be in format 79207865432")
    @NotNull String oldPhone,
    @Pattern(
            regexp = "^7\\d{10}$",
            message = "Phone must be in format 79207865432")
    @NotNull String phone) {}
