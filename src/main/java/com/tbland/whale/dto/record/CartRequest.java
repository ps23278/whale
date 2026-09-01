package com.tbland.whale.dto.record;

import jakarta.validation.constraints.NotNull;

public record CartRequest(
        @NotNull
        Integer accountId

) {
}
