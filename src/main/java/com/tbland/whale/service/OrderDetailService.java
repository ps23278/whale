package com.tbland.whale.service;

import com.tbland.whale.dto.record.OrderDetailRequest;
import com.tbland.whale.dto.record.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailResponse> getAll();

    List<OrderDetailResponse> getByOrderId(Integer orderId);

    OrderDetailResponse create(OrderDetailRequest request);

    OrderDetailResponse update(Integer id, OrderDetailRequest request);

    OrderDetailResponse findById(Integer id);

    void delete(Integer id);


}
