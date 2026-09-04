package com.tbland.whale.controller.rest;

import com.tbland.whale.dto.record.RoleRequest;
import com.tbland.whale.dto.record.RoleResponse;
import com.tbland.whale.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleRestController {
    private final RoleService roleService;

    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleResponse> getAll() {
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public RoleResponse findById(@PathVariable Integer id) {
        return roleService.findById(id);
    }

    @PostMapping
    public RoleResponse create(@Valid @RequestBody RoleRequest request) {
        return roleService.create(request);
    }

    @PutMapping("/{id}")
    public RoleResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody RoleRequest request
    ) {
        return roleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        roleService.delete(id);
    }
}
