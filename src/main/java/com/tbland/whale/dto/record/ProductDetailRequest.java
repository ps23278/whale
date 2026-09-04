package com.tbland.whale.dto.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductDetailRequest(
        @NotBlank
        String material,
        @NotBlank
        String size,
        @Positive
        BigDecimal weight,
        @Positive
        int productId
) {
}
