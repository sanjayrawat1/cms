package com.github.sanjayrawat1.cms.security.authentication;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * An {@link org.springframework.security.core.Authentication} implementation that contains an IdP Bearer token.
 *
 * @author Sanjay Singh Rawat
 */
public class IdTokenAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;

    private final IdTokenIssuer issuer;

    public IdTokenAuthenticationToken(String token, IdTokenIssuer issuer) {
        super(Collections.emptyList());
        this.token = token;
        this.issuer = issuer;
        setAuthenticated(false);
    }

    public IdTokenAuthenticationToken(String token, IdTokenIssuer issuer, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.token = token;
        this.issuer = issuer;
        setAuthenticated(true);
    }

    public String getToken() {
        return token;
    }

    public IdTokenIssuer getIssuer() {
        return issuer;
    }

    @Override
    public Object getCredentials() {
        return this.getToken();
    }

    @Override
    public Object getPrincipal() {
        return this.getToken();
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }
}
