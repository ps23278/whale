package com.tbland.whale.dto.record;

import java.math.BigDecimal;

public record ProductResponse(
        Integer id,
        String name,
        BigDecimal price,
        Integer quantity,
        String description,
        Integer brandId
) {
}
