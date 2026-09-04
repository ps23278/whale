package com.tbland.whale.mapper;

import com.tbland.whale.dto.record.RoleRequest;
import com.tbland.whale.dto.record.RoleResponse;
import com.tbland.whale.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")

public interface RoleMapper {
    Role toEntity(RoleRequest request);

    RoleResponse toResponse(Role role);
}
