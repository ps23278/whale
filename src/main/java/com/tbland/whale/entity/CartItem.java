package com.tbland.whale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="cart_items",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id","cart_id"})
    }
)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;
}
