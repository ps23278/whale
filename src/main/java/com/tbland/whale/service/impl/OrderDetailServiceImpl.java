package com.tbland.whale.service.impl;

import com.tbland.whale.dto.record.OrderDetailRequest;
import com.tbland.whale.dto.record.OrderDetailResponse;
import com.tbland.whale.entity.Order;
import com.tbland.whale.entity.OrderDetail;
import com.tbland.whale.entity.Product;
import com.tbland.whale.exception.ResourceNotFoundException;
import com.tbland.whale.mapper.OrderDetailMapper;
import com.tbland.whale.repository.OrderDetailRepository;
import com.tbland.whale.repository.OrderRepository;
import com.tbland.whale.repository.ProductRepository;
import com.tbland.whale.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderDetailMapper orderDetailMapper;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository,
                                  OrderRepository orderRepository,
                                  ProductRepository productRepository,
                                  OrderDetailMapper orderDetailMapper
    ){
        this.orderDetailRepository = orderDetailRepository;
        this.orderDetailMapper = orderDetailMapper;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public List<OrderDetailResponse> getAll() {
        return orderDetailRepository.findAll()
                .stream()
                .map(orderDetailMapper::toResponse)
                .toList();
    }

    @Override
    public OrderDetailResponse create(OrderDetailRequest request) {
        OrderDetail orderDetail = orderDetailMapper.toEntity(request);

        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy đơn đặt hàng có id: "+request.orderId())
                        );
        Product product = productRepository.findById(request.productId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy sản phẩm có id: "+request.productId())
                        );

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);

        OrderDetail savedOrderdetail = orderDetailRepository.save(orderDetail);

        return orderDetailMapper.toResponse(savedOrderdetail);
    }

    @Override
    public OrderDetailResponse update(Integer id, OrderDetailRequest request) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy đơn hàng chi tiết có id: "+id)
                        );
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(()->
                                new ResourceNotFoundException("Không tìm thấy đơn hàng có id: "+request.orderId())
                                );
        Product product = productRepository.findById(request.productId())
                .orElseThrow(()->
                                new ResourceNotFoundException("Không tìm thấy sản phẩm có id: " +request.productId())
                                );
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setQuantity(request.quantity());
        orderDetail.setUnitPrice(request.unitPrice());

        OrderDetail orderDetailUpdated = orderDetailRepository.save(orderDetail);

        return  orderDetailMapper.toResponse(orderDetailUpdated);
    }

    @Override
    public OrderDetailResponse findById(Integer id) {
        OrderDetail orderDetail = orderDetailRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy chi tiết  đơn hàng có id: "+id)
                        );
        return orderDetailMapper.toResponse(orderDetail);
    }

    @Override
    public void delete(Integer id) {
        orderDetailRepository.deleteById(id);

    }
}
