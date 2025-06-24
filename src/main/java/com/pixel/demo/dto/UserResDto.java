package com.pixel.demo.dto;

import java.time.LocalDate;
import java.util.List;

public record UserResDto(Long id,
                         String name,
                         LocalDate dateOfBirth,
                         List<EmaiReslDto> emailDataList,
                         List<PhoneResDto> phoneDataList) {
}
