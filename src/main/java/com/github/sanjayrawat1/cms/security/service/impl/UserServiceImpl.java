package com.github.sanjayrawat1.cms.security.service.impl;

import com.github.sanjayrawat1.cms.security.authentication.SecurityUtil;
import com.github.sanjayrawat1.cms.security.domain.User;
import com.github.sanjayrawat1.cms.security.exception.EmailAlreadyUsedException;
import com.github.sanjayrawat1.cms.security.exception.UserNotFoundException;
import com.github.sanjayrawat1.cms.security.exception.UsernameAlreadyUsedException;
import com.github.sanjayrawat1.cms.security.repository.RoleRepository;
import com.github.sanjayrawat1.cms.security.repository.UserRepository;
import com.github.sanjayrawat1.cms.security.service.UserService;
import com.github.sanjayrawat1.cms.security.service.dto.UserDto;
import com.github.sanjayrawat1.cms.security.service.mapper.UserMapper;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sanjay Singh Rawat
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    @Override
    public UserDto register(UserDto userDto) {
        userRepository
            .findOneByUsername(userDto.getUsername().toLowerCase())
            .ifPresent(user -> {
                throw new UsernameAlreadyUsedException();
            });
        userRepository
            .findOneByEmailIgnoreCase(userDto.getEmail())
            .ifPresent(user -> {
                throw new EmailAlreadyUsedException();
            });
        User user = userMapper.toEntity(userDto);
        user.getRoles().removeIf(role -> roleRepository.findById(role.getName()).isEmpty());
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto create(UserDto userDto, String password) {
        userRepository
            .findOneByUsername(userDto.getUsername().toLowerCase())
            .ifPresent(user -> {
                throw new UsernameAlreadyUsedException();
            });
        userRepository
            .findOneByEmailIgnoreCase(userDto.getEmail())
            .ifPresent(user -> {
                throw new EmailAlreadyUsedException();
            });
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(password));
        user.getRoles().removeIf(role -> roleRepository.findById(role.getName()).isEmpty());
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto update(UserDto userDto) {
        User user = userRepository.findOneByUsername(userDto.getUsername().toLowerCase()).orElseThrow(UserNotFoundException::new);
        userMapper.update(user, userDto);
        user.getRoles().removeIf(role -> roleRepository.findById(role.getName()).isEmpty());
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public Optional<UserDto> findOneByUsername(String username) {
        return userRepository.findOneByUsername(username).map(userMapper::toDto);
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public Optional<UserDto> activate(String username) {
        return userRepository
            .findOneByUsername(username)
            .map(user -> {
                user.setActivated(true);
                return user;
            })
            .map(userMapper::toDto);
    }

    @Override
    public void delete(String username) {
        userRepository
            .findOneByUsername(username)
            .ifPresentOrElse(
                userRepository::delete,
                () -> {
                    throw new UserNotFoundException();
                }
            );
    }

    @Override
    public Optional<UserDto> getCurrentUser() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneByUsername).map(userMapper::toDto);
    }

    @Override
    public Set<String> getCurrentUserAuthorities() {
        return SecurityUtil
            .getCurrentUsername()
            .flatMap(userRepository::findOneWithRolesAndPrivilegesByUsername)
            .map(user -> SecurityUtil.extractAuthorities(user).stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
            .orElseThrow(() -> new RuntimeException("User authorities could not be found"));
    }
}
