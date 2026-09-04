package com.tbland.whale.service;

import com.tbland.whale.dto.record.AccountRequest;
import com.tbland.whale.dto.record.AccountResponse;
import com.tbland.whale.entity.Account;

import java.util.List;

public interface AccountService {
    List<AccountResponse> getAll();

    AccountResponse getById(Integer id);

    AccountResponse create(AccountRequest request);

    AccountResponse update(Integer id, AccountRequest request);

    void delete(Integer id);

    AccountResponse findByEmail(String email);


}
