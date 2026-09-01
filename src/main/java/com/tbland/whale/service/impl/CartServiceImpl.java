package com.tbland.whale.service.impl;

import com.tbland.whale.entity.Cart;
import com.tbland.whale.repository.CartRepository;
import com.tbland.whale.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository){
        this.cartRepository = cartRepository;
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart findById(Integer id) {
        return cartRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Cart create(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart update(Integer id, Cart cart) {
        Cart existingCart = cartRepository.findById(id)
                .orElse(null);
        if(existingCart == null){
            return null;
        }
        existingCart.setAccount(cart.getAccount());
        return cartRepository.save(existingCart);
    }

    @Override
    public void delete(Integer id) {
        cartRepository.deleteById(id);

    }
}
