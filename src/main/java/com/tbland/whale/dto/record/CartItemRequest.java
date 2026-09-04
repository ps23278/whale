package com.tbland.whale.dto.record;

import com.tbland.whale.entity.Cart;
import com.tbland.whale.entity.Product;
import jakarta.validation.constraints.*;

public record CartItemRequest(
        @Positive
        int quantity,
        @Positive
        int productId,
        @Positive
        int cartId

) {
}
