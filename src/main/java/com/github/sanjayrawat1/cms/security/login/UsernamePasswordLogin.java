package com.github.sanjayrawat1.cms.security.login;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;

/**
 * An {@link Login} implementation that is designed for simple representation of a username and password.
 *
 * @author Sanjay Singh Rawat
 */
public record UsernamePasswordLogin(@NotBlank String username, @NotBlank String password) implements Login, Serializable {
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + "username = " + username + ", " + "password = [PROTECTED], " + ")";
    }
}
