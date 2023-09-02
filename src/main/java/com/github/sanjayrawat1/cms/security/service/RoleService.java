package com.github.sanjayrawat1.cms.security.service;

import com.github.sanjayrawat1.cms.security.service.dto.RoleDto;
import java.util.List;

/**
 * @author Sanjay Singh Rawat
 */
public interface RoleService {
    RoleDto create(RoleDto roleDto);

    RoleDto update(RoleDto roleDto);

    List<RoleDto> getAll();
}
