package com.tbland.whale.service;

import com.tbland.whale.dto.record.CartItemRequest;
import com.tbland.whale.dto.record.CartItemResponse;

import java.util.List;

public interface CartItemService {
    List<CartItemResponse> findAll();

    CartItemResponse create(CartItemRequest request);

    CartItemResponse update(Integer id, CartItemRequest request);

    CartItemResponse findById(Integer id);

    void delete(Integer id);

}
