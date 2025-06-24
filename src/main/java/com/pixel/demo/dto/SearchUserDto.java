package com.pixel.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record SearchUserDto(
        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate dateOfBirth,
        @Pattern(
                regexp = "^7\\d{10}$",
                message = "Phone must be in format 79207865432")
        String phone,
        String name,
        @Email(message = "email must be correct")
        String email,
        Integer page,
        Integer size) {

        public SearchUserDto {
                page = page != null ? page : 0;
                size = size != null ? size : 10;
        }
}
