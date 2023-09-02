package com.github.sanjayrawat1.cms.security.exception;

/**
 * @author Sanjay Singh Rawat
 */
public class UsernameAlreadyUsedException extends BadRequestException {

    public UsernameAlreadyUsedException() {
        super("Username is already in use");
    }

    public UsernameAlreadyUsedException(String detail) {
        super(detail);
    }
}
