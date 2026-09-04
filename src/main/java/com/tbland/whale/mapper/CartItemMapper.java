package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.CartItemRequest;
import com.tbland.whale.dto.record.CartItemResponse;
import com.tbland.whale.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel= "spring")
public interface CartItemMapper {
    CartItem toEntity(CartItemRequest request);

    @Mapping(source="cart.id",target="cartId")
    @Mapping(source="product.id",target="productId")
    CartItemResponse toResponse(CartItem cartItem);
}
