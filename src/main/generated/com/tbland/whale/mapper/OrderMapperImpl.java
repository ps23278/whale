package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.OrderRequest;
import com.tbland.whale.dto.record.OrderResponse;
import com.tbland.whale.entity.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-01T08:46:13+0700",
    comments = "version: 1.7.0.Beta2, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public Order toEntity(OrderRequest orderRequest) {
        if ( orderRequest == null ) {
            return null;
        }

        Order order = new Order();

        return order;
    }

    @Override
    public OrderResponse toResponse(Order order) {
        if ( order == null ) {
            return null;
        }

        Integer id = null;
        String address = null;
        String status = null;
        LocalDateTime orderDate = null;
        BigDecimal totalAmount = null;
        Integer accountId = null;

        OrderResponse orderResponse = new OrderResponse( id, address, status, orderDate, totalAmount, accountId );

        return orderResponse;
    }
}
