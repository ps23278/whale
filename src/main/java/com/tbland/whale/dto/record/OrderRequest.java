package com.tbland.whale.dto.record;

import jakarta.validation.constraints.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderRequest(
        @NotNull
        LocalDateTime orderDate,
        @NotBlank
        String address,
        @NotBlank
        String status,
        @NotNull
        Integer accountId,
        @NotNull
        @Positive
        BigDecimal totalAmount

) {
}
