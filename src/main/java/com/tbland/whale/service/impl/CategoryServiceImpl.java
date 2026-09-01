package com.tbland.whale.service.impl;

import com.tbland.whale.entity.Category;
import com.tbland.whale.repository.CategoryRepository;
import com.tbland.whale.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
     private final CategoryRepository categoryRepository;

     public CategoryServiceImpl(CategoryRepository categoryRepository){
         this.categoryRepository = categoryRepository;
     }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(Integer id) {
        return categoryRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Integer id, Category category) {
         Category existingCategory = categoryRepository.findById(id)
                 .orElse(null);
         if(existingCategory==null){
             return null;
         }
         existingCategory.setName(category.getName());

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(Integer id) {
         categoryRepository.deleteById(id);

    }
}
