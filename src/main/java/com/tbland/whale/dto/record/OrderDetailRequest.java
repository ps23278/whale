package com.tbland.whale.dto.record;

import jakarta.validation.constraints.Positive;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;

public record OrderDetailRequest(
        @Positive
        int orderId,
        @Positive
        int productId,
        @Positive
        int quantity,
        @Positive
        BigDecimal unitPrice
) {
}
