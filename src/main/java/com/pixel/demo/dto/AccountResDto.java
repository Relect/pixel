package com.pixel.demo.dto;

import java.math.BigDecimal;

public record AccountResDto(Long id,
                           Long userId,
                           BigDecimal balance) {}
