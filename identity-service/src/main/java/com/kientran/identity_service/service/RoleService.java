package com.kientran.identity_service.service;

import com.kientran.identity_service.dto.RoleDto;
import java.util.List;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto);
    void deleteRole(Integer roleId);
    RoleDto updateRole(RoleDto roleDto, Integer roleId);
    List<RoleDto> getRoles();
}
