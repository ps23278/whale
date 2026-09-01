package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.ProductRequest;
import com.tbland.whale.dto.record.ProductResponse;
import com.tbland.whale.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface
ProductMapper {
    Product toEntity(ProductRequest request);
    @Mapping(target = "brandId", source = "brand.id")
    ProductResponse toResponse(Product product);
}
