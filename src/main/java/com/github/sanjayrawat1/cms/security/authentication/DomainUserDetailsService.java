package com.github.sanjayrawat1.cms.security.authentication;

import com.github.sanjayrawat1.cms.security.domain.User;
import com.github.sanjayrawat1.cms.security.repository.UserRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sanjay Singh Rawat
 */
@Slf4j
@RequiredArgsConstructor
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> maybeUser = userRepository.findOneWithRolesAndPrivilegesByEmailIgnoreCase(username);
        if (maybeUser.isPresent()) {
            return maybeUser.map(user -> createSpringSecurityUser(username, user)).get();
        }
        String lowercaseUsername = username.toLowerCase(Locale.ENGLISH);
        return userRepository
            .findOneWithRolesAndPrivilegesByUsername(lowercaseUsername)
            .map(user -> createSpringSecurityUser(lowercaseUsername, user))
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String username, User user) {
        if (!user.isActivated()) {
            throw new DisabledException("User is not activated");
        }
        List<GrantedAuthority> authorities = SecurityUtil.extractAuthorities(user);
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    }
}
