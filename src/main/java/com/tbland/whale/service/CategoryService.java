package com.tbland.whale.service;

import com.tbland.whale.dto.record.CategoryRequest;
import com.tbland.whale.dto.record.CategoryResponse;
import com.tbland.whale.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();

    CategoryResponse getById(Integer id);

    CategoryResponse  create(CategoryRequest request);

    CategoryResponse update(Integer id, CategoryRequest reqest);

    void delete(Integer id);



}
