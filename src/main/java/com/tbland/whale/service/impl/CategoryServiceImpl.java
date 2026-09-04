package com.tbland.whale.service.impl;

import com.tbland.whale.dto.record.CategoryRequest;
import com.tbland.whale.dto.record.CategoryResponse;
import com.tbland.whale.entity.Category;
import com.tbland.whale.exception.ResourceNotFoundException;
import com.tbland.whale.mapper.CategoryMapper;
import com.tbland.whale.repository.CategoryRepository;
import com.tbland.whale.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
     private final CategoryRepository categoryRepository;
     private final CategoryMapper categoryMapper;

     public CategoryServiceImpl(CategoryRepository categoryRepository,
                                CategoryMapper categoryMapper
                                ){
         this.categoryRepository = categoryRepository;
         this.categoryMapper = categoryMapper;
     }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse getById(Integer id) {
         Category category = categoryRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy loại sản phẩm có id: "+id)
                        );
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {
         Category category  = categoryMapper.toEntity(request);
         Category savedCategory = categoryRepository.save(category);

         return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse update(Integer id, CategoryRequest request) {
         Category category = categoryRepository.findById(id)
                 .orElseThrow(()->
                         new ResourceNotFoundException("Không tìm thấy loại sản phẩm có id: "+id)
                         );
         category.setName(request.name());

         Category categoryUpdated = categoryRepository.save(category);

        return categoryMapper.toResponse(categoryUpdated);
    }

    @Override
    public void delete(Integer id) {
         categoryRepository.deleteById(id);

    }
}
