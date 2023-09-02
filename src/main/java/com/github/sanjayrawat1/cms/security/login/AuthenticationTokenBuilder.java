package com.github.sanjayrawat1.cms.security.login;

import com.github.sanjayrawat1.cms.security.authentication.IdTokenAuthenticationToken;
import com.github.sanjayrawat1.cms.security.exception.InvalidLoginTypeException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * @author Sanjay Singh Rawat
 */
public final class AuthenticationTokenBuilder {

    private AuthenticationTokenBuilder() {}

    public static Authentication build(Login login) {
        if (login instanceof IdTokenLogin idTokenLogin) {
            return new IdTokenAuthenticationToken(idTokenLogin.token(), idTokenLogin.issuer());
        }
        if (login instanceof UsernamePasswordLogin usernamePasswordLogin) {
            return new UsernamePasswordAuthenticationToken(usernamePasswordLogin.username(), usernamePasswordLogin.password());
        }
        throw new InvalidLoginTypeException();
    }
}
