package com.tbland.whale.service.impl;

import com.tbland.whale.dto.record.ProductRequest;
import com.tbland.whale.dto.record.ProductResponse;
import com.tbland.whale.entity.Brand;
import com.tbland.whale.entity.Product;
import com.tbland.whale.mapper.BrandMapper;
import com.tbland.whale.mapper.ProductMapper;
import com.tbland.whale.repository.BrandRepository;
import com.tbland.whale.repository.ProductRepository;
import com.tbland.whale.service.ProductService;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public ProductServiceImpl (ProductRepository productRepository,
                               ProductMapper productMapper,
                               BrandRepository brandRepository
    ){
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.brandRepository = brandRepository;
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public ProductResponse getById(Integer id) {
//        return productRepository.findById(id)
//                .orElse(null);
        Product product = productRepository.findById(id)
            .orElse(null);
        if(product == null){
            return null;
        }
        return productMapper.toResponse(product);
    }
    @Override
    public ProductResponse create(ProductRequest request){
//        return productRepository.save(product);
        Product product = productMapper.toEntity(request);
        System.out.println("PRICE REQUEST = " + request.price());
        System.out.println("PRICE PRODUCT = " + product.getPrice());
        System.out.println("QUANTITY REQUEST = " + request.quantity());
        System.out.println("QUANTITY PRODUCT = " + product.getQuantity());
        Brand brand = brandRepository.findById(request.brandId())
                .orElse(null);
        product.setBrand(brand);
        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse update(Integer id,ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElse(null);
        if (product == null) {
            return null;
        }
//        }
//        existingProduct.setName(product.getName());
//        existingProduct.setBrand(product.getBrand());
//        existingProduct.setDescription(product.getDescription());
//        existingProduct.setQuantity(product.getQuantity());
//        existingProduct.setPrice(product.getPrice());

//        return productRepository.save(existingProduct

            product.setName(request.name());
            product.setPrice(request.price());
            product.setQuantity(request.quantity());
            product.setDescription(request.description());

            Brand brand = brandRepository.findById(request.brandId())
                    .orElse(null);
            product.setBrand(brand);

            Product updatedProduct = productRepository.save(product);
            return productMapper.toResponse(updatedProduct);

    }
    @Override
    public void delete(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> searchByName(String name) {
        return productRepository.findByNameIgnoreCase(name)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }
}
