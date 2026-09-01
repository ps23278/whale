package com.tbland.whale.dto.record;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Integer id,
        String address,
        String status,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        Integer accountId
        ) {
}
