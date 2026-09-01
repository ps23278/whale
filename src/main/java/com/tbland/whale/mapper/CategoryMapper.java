package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.CategoryRequest;
import com.tbland.whale.dto.record.CategoryResponse;
import com.tbland.whale.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequest request);
    CategoryResponse toResponse(Category category);
}
