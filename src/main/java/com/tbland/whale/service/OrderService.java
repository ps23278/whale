package com.tbland.whale.service;

import com.tbland.whale.dto.record.OrderRequest;
import com.tbland.whale.dto.record.OrderResponse;
import com.tbland.whale.entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getAll();

    List<OrderResponse> getByAccountId(Integer accountId);

    OrderResponse findById(Integer id);

    OrderResponse create(OrderRequest request);

    OrderResponse update(Integer id, OrderRequest request);

    void delete(Integer id);



}
