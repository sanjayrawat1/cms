package com.github.sanjayrawat1.cms.security.exception;

/**
 * @author Sanjay Singh Rawat
 */
public class EmailAlreadyUsedException extends BadRequestException {

    public EmailAlreadyUsedException() {
        super("Email is already in use");
    }

    public EmailAlreadyUsedException(String detail) {
        super(detail);
    }
}
