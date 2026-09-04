package com.tbland.whale.controller.rest;

import com.tbland.whale.dto.record.BrandRequest;
import com.tbland.whale.dto.record.BrandResponse;
import com.tbland.whale.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandRestController {

    private final BrandService brandService;

    public BrandRestController(BrandService brandService){
        this.brandService = brandService;
    }

    @GetMapping
    public List<BrandResponse> getAll() {
        return brandService.getAll();
    }
    @GetMapping("/{id}")
    public BrandResponse findById(@PathVariable Integer id){
        return brandService.findById(id);
    }
    @PostMapping
    public BrandResponse create(@Valid @RequestBody BrandRequest request){
        return brandService.create(request);
    }
    @PutMapping("/{id}")
    public BrandResponse update(@PathVariable Integer id,
                                @Valid @RequestBody BrandRequest request){
        return brandService.update(id,request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        brandService.delete(id);
    }





}
