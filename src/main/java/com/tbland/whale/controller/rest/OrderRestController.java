package com.tbland.whale.controller.rest;

import com.tbland.whale.entity.Order;
import com.tbland.whale.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController (OrderService orderService){
        this.orderService = orderService;
    }
    @GetMapping
    public List<Order> getAll(){
        return orderService.getAll();
    }
    @GetMapping("/{id}")
    public Order findById(@PathVariable Integer id){
        return orderService.findById(id);
    }
    @PostMapping
    public Order create(@RequestBody Order order){
        return orderService.create(order);
    }
    @PutMapping("/{id}")
    public Order update(@PathVariable Integer id, @RequestBody Order order){
        return orderService.update(id,order);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        orderService.delete(id);
    }


}
