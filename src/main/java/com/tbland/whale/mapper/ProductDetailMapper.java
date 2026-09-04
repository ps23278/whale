package com.tbland.whale.mapper;


import com.tbland.whale.dto.record.ProductDetailRequest;
import com.tbland.whale.dto.record.ProductDetailResponse;
import com.tbland.whale.entity.ProductDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface ProductDetailMapper {
    ProductDetail toEntity(ProductDetailRequest request);

    @Mapping(source="product.id", target = "productId")
    ProductDetailResponse toResponse(ProductDetail productDetail);

}
