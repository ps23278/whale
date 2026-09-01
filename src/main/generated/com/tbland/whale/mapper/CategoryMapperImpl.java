package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.CategoryRequest;
import com.tbland.whale.dto.record.CategoryResponse;
import com.tbland.whale.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-01T08:46:13+0700",
    comments = "version: 1.7.0.Beta2, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toEntity(CategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        Category category = new Category();

        return category;
    }

    @Override
    public CategoryResponse toResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        Integer id = null;
        String name = null;

        CategoryResponse categoryResponse = new CategoryResponse( id, name );

        return categoryResponse;
    }
}
