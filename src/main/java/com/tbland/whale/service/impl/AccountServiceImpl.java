package com.tbland.whale.service.impl;

import com.tbland.whale.entity.Account;
import com.tbland.whale.repository.AccountRepository;
import com.tbland.whale.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(Integer id) {
        return accountRepository.findById(id)
                .orElse(null);
    }
    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(Integer id, Account account) {
        Account existingAccount = accountRepository.findById(id)
                .orElse(null);
        if(existingAccount == null){
            return null;
        }
        existingAccount.setUsername(account.getUsername());
        existingAccount.setPassword(account.getPassword());
        existingAccount.setEmail(account.getEmail());
        existingAccount.setPhoto(account.getPhoto());
        existingAccount.setActivated(account.isActivated());

        return accountRepository.save(existingAccount);
    }

    @Override
    public void delete(Integer id) {
        accountRepository.deleteById(id);


    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElse(null);
    }
}
