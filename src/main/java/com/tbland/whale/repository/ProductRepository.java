package com.tbland.whale.repository;

import com.tbland.whale.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    @EntityGraph(attributePaths = "productImages")
    List<Product> findAll();
    List<Product> findByNameIgnoreCase(String name);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    @EntityGraph(attributePaths = "productImages")
    // mot san pham co the co nhieu category
    List<Product> findDistinctByProductCategoriesCategoryId(Integer categoryId);
}
    