package com.tbland.whale.service;

import com.tbland.whale.entity.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAll();

    Account getById(Integer id);

    Account create(Account account);

    Account update(Integer id, Account account);

    void delete(Integer id);

    Account findByEmail(String email);


}
