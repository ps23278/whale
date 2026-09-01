package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.CartRequest;
import com.tbland.whale.dto.record.CartResponse;
import com.tbland.whale.entity.Cart;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-01T08:46:13+0700",
    comments = "version: 1.7.0.Beta2, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Override
    public Cart toEntity(CartRequest request) {
        if ( request == null ) {
            return null;
        }

        Cart cart = new Cart();

        return cart;
    }

    @Override
    public CartResponse toResponse(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        Integer id = null;
        Integer accountId = null;

        CartResponse cartResponse = new CartResponse( id, accountId );

        return cartResponse;
    }
}
