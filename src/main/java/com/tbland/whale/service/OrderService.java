package com.tbland.whale.service;

import com.tbland.whale.entity.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    Order findById(Integer id);

    Order create(Order order);

    Order update(Integer id, Order order);

    void delete(Integer id);

}
