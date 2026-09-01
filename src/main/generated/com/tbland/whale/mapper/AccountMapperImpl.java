package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.AccountRequest;
import com.tbland.whale.dto.record.AccountResponse;
import com.tbland.whale.entity.Account;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-01T08:46:13+0700",
    comments = "version: 1.7.0.Beta2, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toEntity(AccountRequest request) {
        if ( request == null ) {
            return null;
        }

        Account account = new Account();

        return account;
    }

    @Override
    public AccountResponse toResponse(Account account) {
        if ( account == null ) {
            return null;
        }

        Integer id = null;
        String username = null;
        String email = null;
        String photo = null;
        boolean activated = false;

        AccountResponse accountResponse = new AccountResponse( id, username, email, photo, activated );

        return accountResponse;
    }
}
