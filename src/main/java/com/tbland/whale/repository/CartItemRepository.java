package com.tbland.whale.repository;

import com.tbland.whale.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    List<CartItem> findByCartId(Integer cartId);
}
