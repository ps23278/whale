package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.AccountRequest;
import com.tbland.whale.dto.record.AccountResponse;
import com.tbland.whale.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(AccountRequest request);
        AccountResponse toResponse(Account account);
}
