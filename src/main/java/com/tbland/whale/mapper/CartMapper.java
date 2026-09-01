package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.CartRequest;
import com.tbland.whale.dto.record.CartResponse;
import com.tbland.whale.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel ="spring")
public interface CartMapper {
    Cart toEntity(CartRequest request);
    CartResponse toResponse(Cart cart);
    // sau khi hoan tat mapper ta se qua service de hoan thanh not no
}
