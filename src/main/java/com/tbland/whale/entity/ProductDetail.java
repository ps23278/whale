package com.tbland.whale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product_details")
public class ProductDetail {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String material;
    private String size;
    @Column(precision =10,scale =2 )
    private BigDecimal weight;
    @OneToOne
    @JoinColumn(name="product_id", nullable=false,unique=true)
    private Product product;


}
