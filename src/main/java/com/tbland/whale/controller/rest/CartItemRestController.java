package com.tbland.whale.controller.rest;

import com.tbland.whale.dto.record.CartItemRequest;
import com.tbland.whale.dto.record.CartItemResponse;
import com.tbland.whale.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemRestController {
    private final CartItemService cartItemService;

    public CartItemRestController(CartItemService cartItemService){
        this.cartItemService = cartItemService;
    }
@GetMapping
    public List<CartItemResponse> getAll(){
        return cartItemService.findAll();
}
@GetMapping("/{id}")
    public CartItemResponse findById(@PathVariable Integer id){
        return cartItemService.findById(id);
}
@PostMapping
    public CartItemResponse create(@Valid @RequestBody CartItemRequest request){
        return cartItemService.create(request);
}
@PutMapping("/{id}")
    public CartItemResponse update(@PathVariable Integer id,
                                   @Valid @RequestBody CartItemRequest request){
        return cartItemService.update(id,request);
}
@DeleteMapping("/{id}")
    public void  delete(@PathVariable Integer id){
        cartItemService.delete(id);
}
}
