package com.github.sanjayrawat1.cms.security.service.impl;

import com.github.sanjayrawat1.cms.security.domain.Privilege;
import com.github.sanjayrawat1.cms.security.repository.PrivilegeRepository;
import com.github.sanjayrawat1.cms.security.service.PrivilegeService;
import java.util.List;
import java.util.stream.Collectors;
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
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<String> getAll() {
        return privilegeRepository.findAll().stream().map(Privilege::getName).collect(Collectors.toList());
    }
}
