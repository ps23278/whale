package com.tbland.whale.controller.rest;

import com.tbland.whale.dto.record.ProductRequest;
import com.tbland.whale.dto.record.ProductResponse;
import com.tbland.whale.entity.Product;
import com.tbland.whale.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping
    public List<ProductResponse> getAll(){
      return productService.getAll();
    }
    @GetMapping("/category/{categoryId}")
    public List<ProductResponse> getByCategory(
            @PathVariable Integer categoryId
    ) {
        return productService.getByCategory(categoryId);
    }
    @GetMapping("/{id}")
    public  ProductResponse getById(@PathVariable Integer id){
        return productService.getById(id);

    }
    @PostMapping
    public ProductResponse create(@Valid @RequestBody ProductRequest request){
        return productService.create(request);
    }
    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Integer id,
                          @Valid @RequestBody ProductRequest request){
        return productService.update(id,request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        productService.delete(id);
    }
    @GetMapping("/search")
    public  List<ProductResponse> findByName(
            @RequestParam String keyword
    ){
        return productService.searchByName(keyword);
    }





}

