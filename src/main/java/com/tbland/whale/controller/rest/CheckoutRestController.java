package com.tbland.whale.controller.rest;

import com.tbland.whale.dto.record.CheckoutRequest;
import com.tbland.whale.dto.record.OrderResponse;
import com.tbland.whale.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutRestController {

    private final CheckoutService checkoutService;

    public CheckoutRestController(CheckoutService checkoutService){
        this.checkoutService = checkoutService;
    }
    @PostMapping
    public ResponseEntity<OrderResponse> checkout(
            @Valid @RequestBody CheckoutRequest request
    ){
        return ResponseEntity.ok(
                checkoutService.checkout(request)
        );
    }


}
