package com.github.sanjayrawat1.cms.security.repository;

import com.github.sanjayrawat1.cms.security.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sanjay Singh Rawat
 */
@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, String> {}
