package com.tbland.whale.service.impl;

import com.tbland.whale.dto.record.BrandRequest;
import com.tbland.whale.dto.record.BrandResponse;
import com.tbland.whale.entity.Brand;
import com.tbland.whale.mapper.BrandMapper;
import com.tbland.whale.repository.BrandRepository;
import com.tbland.whale.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository,
                            BrandMapper brandMapper
                            ){
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public List<BrandResponse> getAll() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toResponse)
                .toList();
    }

    @Override
    public BrandResponse findById(Integer id) {
        Brand brand = brandRepository.findById(id)
                .orElse(null);
        if(brand==null){
            return null;
        }
        return brandMapper.toResponse(brand);
    }

    @Override
    public BrandResponse create(BrandRequest request) {
        Brand brand = brandMapper.toEntity(request);

        Brand savedBrand = brandRepository.save(brand);
        return brandMapper.toResponse(savedBrand);
    }

    @Override
    public BrandResponse update(Integer id, BrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElse(null);
        if (brand == null){
            return null;
        }
        brand.setName(request.name());
        brand.setImage(request.image());

        Brand brandUpdated = brandRepository.save(brand);

        return brandMapper.toResponse(brandUpdated);
    }

    @Override
    public void delete(Integer id) {
        brandRepository.deleteById(id);
    }
}
