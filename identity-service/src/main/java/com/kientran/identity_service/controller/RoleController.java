package com.kientran.identity_service.controller;

import com.kientran.identity_service.dto.RoleDto;
import com.kientran.identity_service.response.ApiResponse;
import com.kientran.identity_service.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/add")
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto roleDto) {
        RoleDto createRole = this.roleService.createRole(roleDto);
        return new ResponseEntity<>(createRole, HttpStatus.CREATED);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponse> deleteRole(@PathVariable Integer roleId) {
        this.roleService.deleteRole(roleId);
        return new ResponseEntity<>(new ApiResponse("Role is deleted successfully!", true),
                HttpStatus.OK);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDto> updateCategory(@RequestBody RoleDto roleDto,
                                                      @PathVariable Integer roleId) {
        RoleDto updatedRole = this.roleService.updateRole(roleDto, roleId);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        List<RoleDto> roles = this.roleService.getRoles();
        return ResponseEntity.ok(roles);
    }
}
