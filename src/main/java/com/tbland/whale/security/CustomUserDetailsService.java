package com.tbland.whale.security;

import com.tbland.whale.entity.Account;
import com.tbland.whale.entity.RoleDetail;
import com.tbland.whale.repository.AccountRepository;
import com.tbland.whale.repository.RoleDetailRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final RoleDetailRepository roleDetailRepository;
    public CustomUserDetailsService(AccountRepository accountRepository,
                                    RoleDetailRepository roleDetailRepository){
        this.accountRepository = accountRepository;
        this.roleDetailRepository = roleDetailRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Account account  = accountRepository.findByEmail(email)
                .orElseThrow(()->
                      new UsernameNotFoundException("Account not found")
                );
        List<RoleDetail> roleDetails =
                roleDetailRepository.findByAccount(account);
        List<SimpleGrantedAuthority> authorities = roleDetails.stream()
                .map(roleDetail ->
                        new SimpleGrantedAuthority(
                                "ROLE_"+ roleDetail.getRole().getRole()
                        )
                )
                .toList();
        return new CustomUserDetails(
                account,
                authorities
        );
    }
}
