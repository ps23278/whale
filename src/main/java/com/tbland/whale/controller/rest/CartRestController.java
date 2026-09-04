package com.tbland.whale.controller.rest;

import com.tbland.whale.dto.record.CartRequest;
import com.tbland.whale.entity.Cart;
import com.tbland.whale.service.CartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.tbland.whale.dto.record.CartResponse;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(CartService cartService){
        this.cartService = cartService;
    }
    @GetMapping
    public List<CartResponse> getAll(){
        return cartService.getAll();
    }
    @GetMapping("/{id}")
    public CartResponse findById(@PathVariable Integer id){
        return cartService.findById(id);
    }
    @PostMapping
    public CartResponse create(@Valid @RequestBody CartRequest request){
        return cartService.create(request);
    }
    @PutMapping("/{id}")
    public CartResponse update(@PathVariable Integer id,
                               @Valid @RequestBody CartRequest request){
        return cartService.update(id,request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable  Integer id){
        cartService.delete(id);
    }
}
