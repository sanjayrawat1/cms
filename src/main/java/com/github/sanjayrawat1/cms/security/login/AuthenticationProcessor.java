package com.github.sanjayrawat1.cms.security.login;

import com.github.sanjayrawat1.cms.security.authentication.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * @author Sanjay Singh Rawat
 */
@Component
@RequiredArgsConstructor
public class AuthenticationProcessor {

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    public String process(Login login) {
        Authentication loginToken = AuthenticationTokenBuilder.build(login);
        Authentication authenticatedToken = authenticationManager.authenticate(loginToken);
        return tokenProvider.createToken(authenticatedToken);
    }
}
