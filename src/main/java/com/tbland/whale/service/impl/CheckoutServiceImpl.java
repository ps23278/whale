package com.tbland.whale.service.impl;

import com.tbland.whale.dto.record.CheckoutRequest;
import com.tbland.whale.dto.record.OrderResponse;
import com.tbland.whale.entity.*;
import com.tbland.whale.exception.ResourceNotFoundException;
import com.tbland.whale.mapper.OrderMapper;
import com.tbland.whale.repository.*;
import com.tbland.whale.service.CheckoutService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final AccountRepository accountRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderMapper orderMapper;

    public CheckoutServiceImpl(
            AccountRepository accountRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository,
            OrderMapper orderMapper
    ) {
        this.accountRepository = accountRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;

    }

    @Override
    @Transactional
    public OrderResponse checkout(CheckoutRequest request) {
        //1 Tim account
        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "khong tim thay tai khoan:" + request.accountId()
                        )
                );
        //2  Tim cart cua account
        Cart cart = cartRepository.findByAccountId(request.accountId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Khong tim thay gio hang cua tai khoan"
                                        + request.accountId()
                        )
                );
        //3 lay cac CartItem
        List<CartItem> cartItems =
                cartItemRepository.findByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Gio hang dang trong");
        }
        // 4 Tao order
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setAddress(request.address());
        order.setStatus("PENDING");
        order.setAccount(account);
        order.setPaymentMethod(request.paymentMethod());

        //Tam thoi 0, lat tinh lai
        order.setTotalAmount(BigDecimal.ZERO);

        Order savedOrder = orderRepository.save(order);
        //5 Tao OrderDetail + tinh tong tien
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            int quantity = cartItem.getQuantity();

            BigDecimal unitPrice = product.getPrice();

            BigDecimal subTotal =
                    unitPrice.multiply(BigDecimal.valueOf(quantity));
            //Tao OrderDetail
            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setOrder(savedOrder);
            orderDetail.setProduct(product);
            orderDetail.setQuantity(quantity);
            orderDetail.setUnitPrice(unitPrice);

            orderDetailRepository.save(orderDetail);
            //Cong tong
            totalAmount = totalAmount.add(subTotal);
        }

        // 6 Cap nhat tong tien Orer
        savedOrder.setTotalAmount(totalAmount);

        orderRepository.save(savedOrder);
        // 7 Xoa cartItem sau khi checkout
        cartItemRepository.deleteAll(cartItems);
        // 8 Tra ve OrderResponse

        return orderMapper.toResponse(savedOrder);
        }

    }



