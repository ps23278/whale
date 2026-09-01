package com.tbland.whale.repository;

import com.tbland.whale.entity.Product;
import com.tbland.whale.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage,Integer> {
}
