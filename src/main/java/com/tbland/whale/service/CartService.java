package com.tbland.whale.service;

import com.tbland.whale.entity.Cart;

import java.util.List;

public interface CartService {

    List<Cart> getAll();

    Cart findById(Integer id);

    Cart create(Cart cart);

    Cart update(Integer id, Cart cart);

    void delete(Integer id);
}
