package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.BrandRequest;
import com.tbland.whale.dto.record.BrandResponse;
import com.tbland.whale.entity.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel ="spring")
public interface BrandMapper {
    Brand toEntity(BrandRequest request);
    BrandResponse toResponse(Brand brand);

}
