package com.tbland.whale.repository;


import com.tbland.whale.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByAccountId(Integer accountId);
}
