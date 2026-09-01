package com.tbland.whale.dto.record;

import jakarta.validation.constraints.*;

public record AccountRequest(

        @NotBlank
        String username,
        @NotBlank
        String password,
        @NotBlank
        @Email
        String email,
        String phot,
        boolean activatied
) {
}
