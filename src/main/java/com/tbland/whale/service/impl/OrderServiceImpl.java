package com.tbland.whale.service.impl;

import com.tbland.whale.entity.Order;
import com.tbland.whale.repository.OrderRepository;
import com.tbland.whale.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl (OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Integer id, Order order) {
        Order existingOrder = orderRepository.findById(id)
                .orElse(null);
        if(existingOrder == null){
            return null;
        }
        existingOrder.setAccount(order.getAccount());
        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setAddress(order.getAddress());
        existingOrder.setStatus(order.getStatus());
        existingOrder.setTotalAmount(order.getTotalAmount());

        return orderRepository.save(existingOrder);
    }

    @Override
    public void delete(Integer id) {
        orderRepository.deleteById(id);

    }
}
