package com.kientran.identity_service.service.impl;

import com.kientran.identity_service.dto.RoleDto;
import com.kientran.identity_service.entity.Role;
import com.kientran.identity_service.exception.ResourceNotFoundException;
import com.kientran.identity_service.repository.RoleRepository;
import com.kientran.identity_service.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Role role = this.modelMapper.map(roleDto, Role.class);
        Role addRole = this.roleRepository.save(role);
        return this.modelMapper.map(addRole, RoleDto.class);
    }

    @Override
    public void deleteRole(Integer roleId) {
        Role role = this.roleRepository.findById(roleId)
                .orElseThrow(()-> new ResourceNotFoundException("Role ","RoleId", roleId));
        this.roleRepository.delete(role);
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto, Integer roleId) {
        Role role = this.roleRepository.findById(roleId).orElseThrow(()-> new ResourceNotFoundException("Role","RoleId", roleId));
        role.setRole(roleDto.getRole());
        Role updatedRole = this.roleRepository.save(role);
        return this.modelMapper.map(updatedRole, RoleDto.class);
    }

    @Override
    public List<RoleDto> getRoles() {
        List<Role> roles = this.roleRepository.findAll();
        List<RoleDto> roleDtos = roles.stream().map((role)-> this.modelMapper.map(role, RoleDto.class)).collect(Collectors.toList());
        return roleDtos;
    }
}
