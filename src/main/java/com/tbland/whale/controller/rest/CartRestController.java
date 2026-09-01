package com.tbland.whale.controller.rest;

import com.tbland.whale.entity.Cart;
import com.tbland.whale.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService){
        this.cartService = cartService;
    }
    @GetMapping
    public List<Cart> getAll(){
        return cartService.getAll();
    }
    @GetMapping("/{id}")
    public Cart findById(@PathVariable Integer id){
        return cartService.findById(id);
    }
    @PostMapping
    public Cart create(@RequestBody Cart cart){
        return cartService.create(cart);
    }
    @PutMapping("/{id}")
    public Cart update(@PathVariable Integer id,
                       @RequestBody Cart cart){
        return cartService.update(id,cart);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable  Integer id){
        cartService.delete(id);
    }
}
