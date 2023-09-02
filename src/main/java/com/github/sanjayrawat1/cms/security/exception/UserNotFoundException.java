package com.github.sanjayrawat1.cms.security.exception;

/**
 * @author Sanjay Singh Rawat
 */
public class UserNotFoundException extends BadRequestException {

    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String detail) {
        super(detail);
    }
}
