package com.tbland.whale.controller.rest;

import com.tbland.whale.dto.record.ProductDetailRequest;
import com.tbland.whale.dto.record.ProductDetailResponse;
import com.tbland.whale.service.ProductDetailService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-details")
public class ProductDetailRestController {
    private final ProductDetailService productDetailService;

    public ProductDetailRestController(ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
    }

    @GetMapping
    public List<ProductDetailResponse> getAll() {
        return productDetailService.getAll();
    }

    @GetMapping("/{id}")
    public ProductDetailResponse findById(@PathVariable Integer id) {
        return productDetailService.findById(id);
    }

    @PostMapping
    public ProductDetailResponse create(
            @Valid @RequestBody ProductDetailRequest request
    ) {
        return productDetailService.create(request);
    }

    @PutMapping("/{id}")
    public ProductDetailResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody ProductDetailRequest request
    ) {
        return productDetailService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        productDetailService.delete(id);
    }
}

