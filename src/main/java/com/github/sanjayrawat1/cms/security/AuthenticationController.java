package com.github.sanjayrawat1.cms.security;

import com.github.sanjayrawat1.cms.security.login.AuthenticationProcessor;
import com.github.sanjayrawat1.cms.security.login.Login;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sanjay Singh Rawat
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationProcessor authenticationProcessor;

    @PostMapping("/v1/authenticate")
    public ResponseEntity<JwtToken> authenticate(@Valid @RequestBody Login login) {
        String jwt = authenticationProcessor.process(login);
        return ResponseEntity.ok().body(new JwtToken(jwt));
    }

    record JwtToken(String idToken) {
        @Override
        public String toString() {
            return getClass().getSimpleName() + "(" + "idToken = [PROTECTED], " + ")";
        }
    }
}
