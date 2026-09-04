package com.tbland.whale.service.impl;

import com.tbland.whale.dto.record.OrderRequest;
import com.tbland.whale.dto.record.OrderResponse;
import com.tbland.whale.entity.Account;
import com.tbland.whale.entity.Order;
import com.tbland.whale.mapper.OrderMapper;
import com.tbland.whale.repository.AccountRepository;
import com.tbland.whale.repository.OrderRepository;
import com.tbland.whale.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AccountRepository accountRepository;

    public OrderServiceImpl (OrderRepository orderRepository,
                             OrderMapper orderMapper,
                             AccountRepository accountRepository){
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<OrderResponse> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderResponse findById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElse(null);
        if(order== null){
            return null;
        }
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse create(OrderRequest request) {
        Order order = orderMapper.toEntity(request);
        Account account = accountRepository.findById(request.accountId())
                .orElse(null);
        if (account == null){
            return null;
        }
        order.setAccount(account);

        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public OrderResponse update(Integer id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElse(null);
        if(order == null){
            return null;
        }
        order.setOrderDate(request.orderDate());
        order.setAddress(request.address());
        order.setStatus(request.status());
        order.setTotalAmount(request.totalAmount());
        Account account = accountRepository.findById(request.accountId())
                .orElse(null);
        if (account == null){
            return null;
        }

        order.setAccount(account);

        Order orderUpdated = orderRepository.save(order);

        return orderMapper.toResponse(orderUpdated);
    }

    @Override
    public void delete(Integer id) {
        orderRepository.deleteById(id);

    }
}
