package com.tbland.whale.dto.record;


public record CartItemResponse(
        int id,
        int quantity,
        int productId,
        int cartId

) {
}
