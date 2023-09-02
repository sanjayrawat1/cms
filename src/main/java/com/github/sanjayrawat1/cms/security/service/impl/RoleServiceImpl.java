package com.github.sanjayrawat1.cms.security.service.impl;

import com.github.sanjayrawat1.cms.security.domain.Role;
import com.github.sanjayrawat1.cms.security.exception.BadRequestException;
import com.github.sanjayrawat1.cms.security.repository.PrivilegeRepository;
import com.github.sanjayrawat1.cms.security.repository.RoleRepository;
import com.github.sanjayrawat1.cms.security.service.RoleService;
import com.github.sanjayrawat1.cms.security.service.dto.RoleDto;
import com.github.sanjayrawat1.cms.security.service.mapper.RoleMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sanjay Singh Rawat
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private static final String ROLE_EXISTS_MESSAGE = "Role %s already exists";

    private final RoleRepository roleRepository;

    private final PrivilegeRepository privilegeRepository;

    private final RoleMapper roleMapper;

    @Override
    public RoleDto create(RoleDto roleDto) {
        roleRepository
            .findById(roleDto.getName())
            .ifPresent(role -> {
                throw new BadRequestException(String.format(ROLE_EXISTS_MESSAGE, roleDto.getName()));
            });
        Role role = roleMapper.toEntity(roleDto);
        role.getPrivileges().removeIf(privilege -> privilegeRepository.findById(privilege.getName()).isEmpty());
        role = roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    @Override
    public RoleDto update(RoleDto roleDto) {
        Role role = roleRepository.findById(roleDto.getName()).orElseThrow(() -> new BadRequestException("Role not found"));
        roleMapper.update(role, roleDto);
        role.getPrivileges().removeIf(privilege -> privilegeRepository.findById(privilege.getName()).isEmpty());
        role = roleRepository.save(role);
        return roleMapper.toDto(role);
    }

    @Override
    public List<RoleDto> getAll() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.toDto(roles);
    }
}
