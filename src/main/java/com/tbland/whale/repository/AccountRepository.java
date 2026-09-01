package com.tbland.whale.repository;

import com.tbland.whale.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
    // boi vi Jpa khong co san findbyemail nen chung ta can khai bao
}
