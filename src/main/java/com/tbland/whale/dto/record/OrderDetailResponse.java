package com.tbland.whale.dto.record;

import java.math.BigDecimal;

public record OrderDetailResponse(
        int id,
        int productId,
        int orderId,
        int quantity,
        BigDecimal unitPrice
) {
}
