package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.OrderRequest;
import com.tbland.whale.dto.record.OrderResponse;
import com.tbland.whale.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface OrderMapper {
    Order toEntity(OrderRequest orderRequest);
    OrderResponse toResponse(Order order);
}
