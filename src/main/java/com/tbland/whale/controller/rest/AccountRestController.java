package com.tbland.whale.controller.rest;

import com.tbland.whale.dto.record.AccountRequest;
import com.tbland.whale.dto.record.AccountResponse;
import com.tbland.whale.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountRestController{

    private final AccountService accountService;

    public AccountRestController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResponse> getAll(){
        return accountService.getAll();
    }
    @GetMapping("/{id}")
    public AccountResponse getById(@Valid @PathVariable Integer id){
        return accountService.getById(id);
    }
    @PostMapping
    public AccountResponse create(@RequestBody AccountRequest request){
        return accountService.create(request);
    }
    @PutMapping("/{id}")
    public AccountResponse update( @PathVariable Integer id,
                                   @Valid @RequestBody AccountRequest request){
        return accountService.update(id,request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        accountService.delete(id);
    }
    @GetMapping("/email")
    public AccountResponse findByEmail(@RequestParam String email){
        return accountService.findByEmail(email);
    }

}
