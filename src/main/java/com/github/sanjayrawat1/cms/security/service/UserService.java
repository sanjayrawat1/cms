package com.github.sanjayrawat1.cms.security.service;

import com.github.sanjayrawat1.cms.security.service.dto.UserDto;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Sanjay Singh Rawat
 */
public interface UserService {
    UserDto register(UserDto userDto);

    UserDto create(UserDto userDto, String password);

    UserDto update(UserDto userDto);

    Optional<UserDto> findOneByUsername(String username);

    Page<UserDto> findAll(Pageable pageable);

    Optional<UserDto> activate(String username);

    void delete(String username);

    Optional<UserDto> getCurrentUser();

    Set<String> getCurrentUserAuthorities();
}
