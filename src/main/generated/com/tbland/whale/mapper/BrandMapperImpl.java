package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.BrandRequest;
import com.tbland.whale.dto.record.BrandResponse;
import com.tbland.whale.entity.Brand;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-01T08:46:12+0700",
    comments = "version: 1.7.0.Beta2, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class BrandMapperImpl implements BrandMapper {

    @Override
    public Brand toEntity(BrandRequest request) {
        if ( request == null ) {
            return null;
        }

        Brand brand = new Brand();

        return brand;
    }

    @Override
    public BrandResponse toResponse(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String image = null;

        BrandResponse brandResponse = new BrandResponse( id, name, image );

        return brandResponse;
    }
}
