package com.tbland.whale.service.impl;

import com.tbland.whale.dto.record.AccountRequest;
import com.tbland.whale.dto.record.AccountResponse;
import com.tbland.whale.entity.Account;
import com.tbland.whale.exception.ResourceNotFoundException;
import com.tbland.whale.mapper.AccountMapper;
import com.tbland.whale.repository.AccountRepository;
import com.tbland.whale.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountMapper accountMapper){
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<AccountResponse> getAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toResponse)
                .toList();
    }

    @Override
    public AccountResponse getById(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("id của tài khoản không tồn tại:"+id)
                        );
        return accountMapper.toResponse(account);
    }
    @Override
    public AccountResponse create(AccountRequest request) {
            Account account = accountMapper.toEntity(request);
//            account.setUsername(request.username());
//            account.setPassword(request.password());
//            account.setEmail(request.email());
//            account.setPhoto(request.photo());
//            account.setActivated(request.activated());

            Account savedAccount = accountRepository.save(account);
        return accountMapper.toResponse(savedAccount);
    }

    @Override
    public AccountResponse update(Integer id, AccountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Id tài khoản không tồn tại: "+id)
                        );
        account.setUsername(request.username());
        account.setPassword(request.password());
        account.setEmail(request.email());
        account.setPhoto(request.photo());
        account.setActivated(request.activated());

        Account accountUpdated =  accountRepository.save(account);

        return accountMapper.toResponse(accountUpdated);
    }

    @Override
    public void delete(Integer id) {
        accountRepository.deleteById(id);


    }

    @Override
    public AccountResponse findByEmail(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(()->
                        new ResourceNotFoundException("Email không tồn tại: "+email)
                        );
        return accountMapper.toResponse(account);
    }
}
