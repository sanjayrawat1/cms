package com.github.sanjayrawat1.cms.security.login;

import com.github.sanjayrawat1.cms.security.authentication.IdTokenIssuer;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * An {@link Login} implementation that is designed for simple representation of a OpenId Connect id_token.
 *
 * @author Sanjay Singh Rawat
 */
public record IdTokenLogin(@NotBlank String token, @NotNull IdTokenIssuer issuer) implements Login, Serializable {
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + "token = [PROTECTED], " + "issuer = " + issuer + ")";
    }
}
