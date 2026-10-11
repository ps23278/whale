package com.tbland.whale.service;

import com.tbland.whale.dto.record.CheckoutRequest;
import com.tbland.whale.dto.record.OrderResponse;

public interface CheckoutService {
    OrderResponse checkout(CheckoutRequest request);
}
