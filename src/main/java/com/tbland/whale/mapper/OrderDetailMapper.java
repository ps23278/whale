package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.OrderDetailRequest;
import com.tbland.whale.dto.record.OrderDetailResponse;
import com.tbland.whale.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetail toEntity(OrderDetailRequest request);

    @Mapping(source ="order.id",target = "orderId")
    @Mapping(source ="product.id",target="productId")
    OrderDetailResponse toResponse(OrderDetail orderDetail);
}
