package com.tbland.whale.service.impl;

import com.tbland.whale.dto.record.ProductDetailRequest;
import com.tbland.whale.dto.record.ProductDetailResponse;
import com.tbland.whale.entity.Product;
import com.tbland.whale.entity.ProductDetail;
import com.tbland.whale.exception.ResourceNotFoundException;
import com.tbland.whale.mapper.ProductDetailMapper;
import com.tbland.whale.repository.ProductDetailRepository;
import com.tbland.whale.repository.ProductRepository;
import com.tbland.whale.service.ProductDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    private final ProductDetailRepository productDetailRepository;
    private final ProductRepository productRepository;
    private final ProductDetailMapper productDetailMapper;

    public ProductDetailServiceImpl(
            ProductDetailRepository productDetailRepository,
            ProductRepository productRepository,
            ProductDetailMapper productDetailMapper
    ) {
        this.productDetailRepository = productDetailRepository;
        this.productRepository = productRepository;
        this.productDetailMapper = productDetailMapper;
    }
    @Override
    public List<ProductDetailResponse> getAll() {
        return productDetailRepository.findAll()
                .stream()
                .map(productDetailMapper::toResponse)
                .toList();
    }

    @Override
    public ProductDetailResponse create(ProductDetailRequest request) {
        ProductDetail productDetail = productDetailMapper.toEntity(request);

        Product product = productRepository.findById(request.productId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy sản phẩm có id: "+request.productId())
                        );

        productDetail.setProduct(product);

        ProductDetail savedProductDetail =
                productDetailRepository.save(productDetail);

        return productDetailMapper.toResponse(savedProductDetail);
    }
    @Override
    public ProductDetailResponse update(Integer id, ProductDetailRequest request) {

        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy chi tiết sản phẩm có id: "+id)
                        );
        Product product = productRepository.findById(request.productId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy sản phẩm có id: "+request.productId())
                        );
        productDetail.setProduct(product);
        productDetail.setMaterial(request.material());
        productDetail.setSize(request.size());
        productDetail.setWeight(request.weight());

        ProductDetail updatedProductDetail =
                productDetailRepository.save(productDetail);

        return productDetailMapper.toResponse(updatedProductDetail);
    }

    @Override
    public void delete(Integer id) {
        productDetailRepository.deleteById(id);

    }

    @Override
    public ProductDetailResponse findById(Integer id) {
        ProductDetail productDetail = productDetailRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy sản phẩm chi tiết có id: "+id)
                        );

        return productDetailMapper.toResponse(productDetail);
    }
}
