package com.tbland.whale.repository;

import com.tbland.whale.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    List<OrderDetail> findByOrderId(Integer orderId);
}
