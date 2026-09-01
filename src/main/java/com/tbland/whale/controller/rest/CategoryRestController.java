package com.tbland.whale.controller.rest;

import com.tbland.whale.entity.Category;
import com.tbland.whale.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController (CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @GetMapping
    public List<Category> getAll(){
        return categoryService.getAll();
    }
    @GetMapping("/{id}")
    public Category findById(@PathVariable Integer id){
        return categoryService.getById(id);
    }
    @PostMapping
    public Category create(@RequestBody Category category){
        return categoryService.create(category);
    }
    @PutMapping("/{id}")
    public Category update(@PathVariable Integer id,@RequestBody Category category){
        return categoryService.update(id,category);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
         categoryService.delete(id);
    }

}
