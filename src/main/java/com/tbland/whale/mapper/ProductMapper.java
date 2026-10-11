package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.ProductRequest;
import com.tbland.whale.dto.record.ProductResponse;
import com.tbland.whale.entity.Product;
import com.tbland.whale.entity.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface
ProductMapper {
    Product toEntity(ProductRequest request);
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target ="images",source ="productImages")
    ProductResponse toResponse(Product product);

    default String map(ProductImage productImage) {
        return productImage.getImage();
    }
}
