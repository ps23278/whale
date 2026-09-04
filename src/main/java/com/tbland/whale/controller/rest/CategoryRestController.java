package com.tbland.whale.controller.rest;

import com.tbland.whale.dto.record.CategoryRequest;
import com.tbland.whale.dto.record.CategoryResponse;
import com.tbland.whale.service.CategoryService;
import jakarta.validation.Valid;
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
    public List<CategoryResponse> getAll(){
        return categoryService.getAll();
    }
    @GetMapping("/{id}")
    public CategoryResponse findById(@PathVariable Integer id){
        return categoryService.getById(id);
    }
    @PostMapping
    public CategoryResponse create(@Valid @RequestBody CategoryRequest request){
        return categoryService.create(request);
    }
    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Integer id,
                                   @Valid @RequestBody CategoryRequest request){
        return categoryService.update(id,request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
         categoryService.delete(id);
    }

}
