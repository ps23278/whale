package com.tbland.whale.controller.rest;

import com.tbland.whale.dto.record.OrderRequest;
import com.tbland.whale.dto.record.OrderResponse;
import com.tbland.whale.entity.Account;
import com.tbland.whale.entity.Order;
import com.tbland.whale.repository.AccountRepository;
import com.tbland.whale.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;
    private final AccountRepository accountRepository;

    public OrderRestController (OrderService orderService,
                                AccountRepository accountRepository)
    {
        this.orderService = orderService;
        this.accountRepository = accountRepository;
    }
    @GetMapping
    public List<OrderResponse> getAll(){
        return orderService.getAll();
    }
    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Integer id){
        return orderService.findById(id);
    }
    @PostMapping
    public OrderResponse create(@Valid @RequestBody OrderRequest request){
        return orderService.create(request);
    }
    @PutMapping("/{id}")
    public OrderResponse update(@PathVariable Integer id,
                                @Valid @RequestBody OrderRequest request){
        return orderService.update(id,request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        orderService.delete(id);
    }
    @GetMapping("/account/{accountId}")
    public List<OrderResponse> getByAccountId(@PathVariable Integer accountId){
        return orderService.getByAccountId(accountId);
    }
    @GetMapping("/my-orders")
    public List<OrderResponse> getMyOrders(Authentication authentication) {

        String email = authentication.getName();

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return orderService.getByAccountId(account.getId());
    }



}
