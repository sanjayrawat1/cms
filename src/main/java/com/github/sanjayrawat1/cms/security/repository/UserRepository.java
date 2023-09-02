package com.github.sanjayrawat1.cms.security.repository;

import com.github.sanjayrawat1.cms.security.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sanjay Singh Rawat
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUsername(String username);

    Optional<User> findOneByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = "roles.privileges")
    Optional<User> findOneWithRolesAndPrivilegesByUsername(String username);

    Optional<User> findOneWithRolesAndPrivilegesByEmailIgnoreCase(String username);
}
