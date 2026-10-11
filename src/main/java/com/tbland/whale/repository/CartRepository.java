package com.tbland.whale.repository;

import com.tbland.whale.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Integer> {
     Optional<Cart> findByAccountId(Integer accountId);
}
