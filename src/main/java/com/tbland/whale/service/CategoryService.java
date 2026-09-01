package com.tbland.whale.service;

import com.tbland.whale.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();

    Category getById(Integer id);

    Category  create(Category category);

    Category update(Integer id, Category category);

    void delete(Integer id);



}
