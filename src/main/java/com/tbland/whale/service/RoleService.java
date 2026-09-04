package com.tbland.whale.service;

import com.tbland.whale.dto.record.RoleRequest;
import com.tbland.whale.dto.record.RoleResponse;
import com.tbland.whale.entity.Role;

import java.util.List;

public interface RoleService {
    List<RoleResponse> getAll();

    RoleResponse findById(Integer id);

    RoleResponse create(RoleRequest request);

    RoleResponse update(Integer id,RoleRequest request);

    void delete(Integer id);


}
