package com.tbland.whale.dto.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleRequest(
        @NotNull
        String role,
        @NotBlank
        String description
) {
}
