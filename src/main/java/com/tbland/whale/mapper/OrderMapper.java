package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.OrderRequest;
import com.tbland.whale.dto.record.OrderResponse;
import com.tbland.whale.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface OrderMapper {
    Order toEntity(OrderRequest orderRequest);

    @Mapping(source = "account.id", target = "accountId")
    OrderResponse toResponse(Order order);

}
