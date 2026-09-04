package com.tbland.whale.service;

import com.tbland.whale.dto.record.CartRequest;
import com.tbland.whale.dto.record.CartResponse;
import com.tbland.whale.entity.Cart;

import java.util.List;

public interface CartService {

    List<CartResponse> getAll();

    CartResponse findById(Integer id);

    CartResponse create(CartRequest request);

    CartResponse update(Integer id, CartRequest request);

    void delete(Integer id);
}
