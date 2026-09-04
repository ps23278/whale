package com.tbland.whale.dto.record;

import java.math.BigDecimal;

public record ProductDetailResponse(
        int id,
        String material,
        String size,
        BigDecimal weight,
        int productId
) {
}
