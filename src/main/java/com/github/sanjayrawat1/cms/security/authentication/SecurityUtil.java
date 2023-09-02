package com.github.sanjayrawat1.cms.security.authentication;

import com.github.sanjayrawat1.cms.security.domain.Privilege;
import com.github.sanjayrawat1.cms.security.domain.Role;
import com.github.sanjayrawat1.cms.security.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Sanjay Singh Rawat
 */
public class SecurityUtil {

    public static Optional<String> getCurrentUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication).getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public static List<GrantedAuthority> extractAuthorities(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        user
            .getRoles()
            .stream()
            .flatMap(role -> role.getPrivileges().stream())
            .map(Privilege::getName)
            .map(SimpleGrantedAuthority::new)
            .forEach(authorities::add);
        return authorities;
    }
}
