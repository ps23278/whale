package com.tbland.whale.dto.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(
        @NotBlank
        String address,
        @NotNull
        Integer accountId,
        String paymentMethod
) {
}
