package com.tbland.whale.dto.record;

import jakarta.validation.constraints.NotBlank;

public record BrandRequest(
        @NotBlank
        String name,
        String image
) {
}
