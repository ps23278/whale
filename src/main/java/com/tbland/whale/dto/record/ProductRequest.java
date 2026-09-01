package com.tbland.whale.dto.record;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;


public record ProductRequest(

        @NotBlank
        String name,
        @NotNull
        @Positive(message="gia tien phai lon hon 0")
        BigDecimal price,
        @NotNull
        @PositiveOrZero
        Integer quantity,
        String description,
        @NotNull
        Integer brandId

) {
}
