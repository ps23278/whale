package com.tbland.whale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

        private int id;
        private String name;
        @Column(precision =15,scale=2)
        private BigDecimal price;
        private int quantity;
        private String description;
        @ManyToOne
        @JoinColumn(name="brand_id")
        private Brand brand;
        @OneToMany(mappedBy = "product")
        private List<ProductImage> productImages;
        @OneToMany(mappedBy = "product")
        private List<ProductCategory> productCategories;


}
