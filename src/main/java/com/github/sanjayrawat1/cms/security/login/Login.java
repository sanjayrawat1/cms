package com.github.sanjayrawat1.cms.security.login;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Contract for authentication request.
 *
 * @author Sanjay Singh Rawat
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
    { @JsonSubTypes.Type(value = IdTokenLogin.class, name = "idToken"), @JsonSubTypes.Type(value = UsernamePasswordLogin.class, name = "usernamePassword") }
)
public sealed interface Login permits IdTokenLogin, UsernamePasswordLogin {}
