package com.pixel.demo.dto;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record AccountReqDto (@Min(1)
                             Long userId,
                             @Min(1)
                             BigDecimal balance) {}
