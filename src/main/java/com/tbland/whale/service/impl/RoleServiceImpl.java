package com.tbland.whale.service.impl;

import com.tbland.whale.dto.record.RoleRequest;
import com.tbland.whale.dto.record.RoleResponse;
import com.tbland.whale.entity.Role;
import com.tbland.whale.exception.ResourceNotFoundException;
import com.tbland.whale.mapper.RoleMapper;
import com.tbland.whale.repository.RoleRepository;
import com.tbland.whale.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    public RoleServiceImpl(
            RoleRepository roleRepository,
            RoleMapper roleMapper
    ) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleResponse> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    @Override
    public RoleResponse findById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy vai trò có id: "+id)

                        );

        return roleMapper.toResponse(role);
    }

    @Override
    public RoleResponse create(RoleRequest request) {
        Role role = roleMapper.toEntity(request);

        Role savedRole = roleRepository.save(role);

        return roleMapper.toResponse(savedRole);
    }

    @Override
    public RoleResponse update(Integer id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Không tìm thấy vai trò có id: "+id)
                        );
        role.setRole(request.role());
        role.setDescription(request.description());

        Role updatedRole = roleRepository.save(role);

        return roleMapper.toResponse(updatedRole);
    }

    @Override
    public void delete(Integer id) {
        roleRepository.deleteById(id);

    }
}
