package com.pixel.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record UserResDto(Long id,
                         String name,
                         @JsonFormat(pattern = "dd.MM.yyyy")
                         LocalDate dateOfBirth,
                         List<EmaiResDto> emailDataList,
                         List<PhoneResDto> phoneDataList) {
}
