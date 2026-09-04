package com.tbland.whale.controller.rest;


import com.tbland.whale.dto.record.OrderDetailRequest;
import com.tbland.whale.dto.record.OrderDetailResponse;
import com.tbland.whale.service.OrderDetailService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailRestController {

    private final OrderDetailService orderDetailService;

    public OrderDetailRestController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping
    public List<OrderDetailResponse> getAll() {
        return orderDetailService.getAll();
    }

    @GetMapping("/{id}")
    public OrderDetailResponse findById(@PathVariable Integer id) {
        return orderDetailService.findById(id);
    }

    @PostMapping
    public OrderDetailResponse create(@Valid @RequestBody OrderDetailRequest request) {
        return orderDetailService.create(request);
    }

    @PutMapping("/{id}")
    public OrderDetailResponse update(
            @PathVariable Integer id,
             @Valid @RequestBody OrderDetailRequest request
    ) {
        return orderDetailService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        orderDetailService.delete(id);
    }
}
