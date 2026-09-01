package com.tbland.whale.service;

import com.tbland.whale.dto.record.ProductRequest;
import com.tbland.whale.dto.record.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAll();

    ProductResponse getById(Integer id);

    ProductResponse create(ProductRequest request);

    ProductResponse update(Integer id,ProductRequest request);

    void delete(Integer id);

    List<ProductResponse> searchByName(String name);
}
