package com.github.sanjayrawat1.cms.security.authentication;

import com.github.sanjayrawat1.cms.security.config.SecurityConfiguration;
import com.github.sanjayrawat1.cms.security.domain.Role;
import com.github.sanjayrawat1.cms.security.domain.User;
import com.github.sanjayrawat1.cms.security.repository.RoleRepository;
import com.github.sanjayrawat1.cms.security.repository.UserRepository;
import com.github.sanjayrawat1.cms.util.InjectionUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sanjay Singh Rawat
 */
@Slf4j
public class IdTokenAuthenticationProvider implements AuthenticationProvider {

    private static final String DEFAULT_AUTHORITY = "ROLE_USER";

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final JwtDecoder microsoftJwtDecoder;

    public IdTokenAuthenticationProvider() {
        this.userRepository = InjectionUtil.getBean(UserRepository.class);
        this.roleRepository = InjectionUtil.getBean(RoleRepository.class);
        this.microsoftJwtDecoder = InjectionUtil.getBean(SecurityConfiguration.MICROSOFT_JWT_DECODER_BEAN_NAME, JwtDecoder.class);
    }

    @Override
    @Transactional(noRollbackFor = DisabledException.class)
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        IdTokenAuthenticationToken token = (IdTokenAuthenticationToken) authentication;
        Jwt jwt = getJwt(token);
        User user = createUserIfNotExists(jwt);
        List<GrantedAuthority> authorities = SecurityUtil.extractAuthorities(user);
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getUsername(), authorities);
    }

    private Jwt getJwt(IdTokenAuthenticationToken token) {
        JwtDecoder jwtDecoder = getJwtDecoder(token.getIssuer());
        try {
            return jwtDecoder.decode(token.getToken());
        } catch (BadJwtException exception) {
            throw new BadCredentialsException(exception.getMessage(), exception);
        } catch (JwtException exception) {
            throw new AuthenticationServiceException(exception.getMessage(), exception);
        }
    }

    private JwtDecoder getJwtDecoder(IdTokenIssuer issuer) {
        if (IdTokenIssuer.MICROSOFT.equals(issuer)) {
            return microsoftJwtDecoder;
        }
        throw new IllegalArgumentException("Invalid token issuer");
    }

    private User createUserIfNotExists(Jwt jwt) {
        User user = buildUser(jwt);
        User dbUser = userRepository.findOneWithRolesAndPrivilegesByUsername(user.getUsername()).orElseGet(() -> userRepository.save(user));
        if (!dbUser.isActivated()) {
            throw new DisabledException("User is not activated, please connect with application administrator to activate your account");
        }
        return dbUser;
    }

    private User buildUser(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        User user = new User();
        if (claims.get("preferred_username") != null) {
            String username = (String) claims.get("preferred_username");
            user.setUsername(username.toLowerCase(Locale.ENGLISH));
        }
        if (claims.get("email") != null) {
            user.setEmail((String) claims.get("email"));
        }
        if (claims.get("name") != null) {
            user.setName((String) claims.get("name"));
        }
        user.setActivated(false);
        Set<Role> roles = new HashSet<>();
        roleRepository.findById(DEFAULT_AUTHORITY).ifPresent(roles::add);
        user.setRoles(roles);
        return user;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return IdTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
