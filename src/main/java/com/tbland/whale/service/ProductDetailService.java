package com.tbland.whale.service;

import com.tbland.whale.dto.record.ProductDetailRequest;
import com.tbland.whale.dto.record.ProductDetailResponse;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetailResponse> getAll();

    ProductDetailResponse create(ProductDetailRequest request);

    ProductDetailResponse update(Integer id, ProductDetailRequest request);

    void delete(Integer id);

    ProductDetailResponse findById(Integer id);

}
