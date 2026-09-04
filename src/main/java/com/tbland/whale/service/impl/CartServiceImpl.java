package com.tbland.whale.service.impl;


import com.tbland.whale.dto.record.CartRequest;
import com.tbland.whale.dto.record.CartResponse;
import com.tbland.whale.entity.Account;
import com.tbland.whale.entity.Cart;
import com.tbland.whale.exception.ResourceNotFoundException;
import com.tbland.whale.mapper.CartMapper;
import com.tbland.whale.repository.AccountRepository;
import com.tbland.whale.repository.CartRepository;
import com.tbland.whale.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final AccountRepository accountRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           CartMapper cartMapper,
                           AccountRepository accountRepository
    ){
        this.cartRepository = cartRepository;
        this.accountRepository = accountRepository;
        this.cartMapper = cartMapper;
    }

    @Override
    public List<CartResponse> getAll() {
        return cartRepository.findAll()
                .stream()
                .map(cartMapper::toResponse)
                .toList();
    }

    @Override
    public CartResponse findById(Integer id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy giỏ hàng id: "+id)
                        );
        return cartMapper.toResponse(cart);
    }

    @Override
    public CartResponse create(CartRequest request) {
        Cart cart = cartMapper.toEntity(request);
        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy tài khoản id: "+request.accountId())
                        );
        cart.setAccount(account);
        Cart savedCart = cartRepository.save(cart);
        return cartMapper.toResponse(savedCart);
    }

    @Override
    public CartResponse update(Integer id, CartRequest request) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy giỏ hàng id: "+id)
                );
        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy tài khoản có id: "+request.accountId())
                        );
        cart.setAccount(account);

        Cart cartUpdated = cartRepository.save(cart);

        return cartMapper.toResponse(cartUpdated);
    }

    @Override
    public void delete(Integer id) {
        cartRepository.deleteById(id);

    }
}
