package com.tbland.whale.controller.rest;

import com.tbland.whale.entity.Account;
import com.tbland.whale.service.AccountService;
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
    public List<Account> getAll(){
        return accountService.getAll();
    }
    @GetMapping("/{id}")
    public Account getById(@PathVariable Integer id){
        return accountService.getById(id);
    }
    @PostMapping
    public Account create(@RequestBody Account account){
        return accountService.create(account);
    }
    @PutMapping("/{id}")
    public Account update(@PathVariable Integer id,
                          @RequestBody Account account){
        return accountService.update(id,account);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        accountService.delete(id);
    }
    @GetMapping("/email")
    public Account findByEmail(@RequestParam String email){
        return accountService.findByEmail(email);
    }

}
