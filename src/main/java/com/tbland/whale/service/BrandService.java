package com.tbland.whale.service;

import com.tbland.whale.dto.record.BrandRequest;
import com.tbland.whale.dto.record.BrandResponse;
import com.tbland.whale.entity.Brand;

import java.util.List;

public interface BrandService {
    List<BrandResponse> getAll();

    BrandResponse findById(Integer id);

    BrandResponse create(BrandRequest request);

    BrandResponse update(Integer id, BrandRequest request);

    void delete(Integer id);

}
