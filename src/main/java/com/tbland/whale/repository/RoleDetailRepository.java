package com.tbland.whale.repository;

import com.tbland.whale.entity.Account;
import com.tbland.whale.entity.RoleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleDetailRepository extends JpaRepository<RoleDetail,Integer> {
    List<RoleDetail> findByAccount(Account account);
}
