package com.github.sanjayrawat1.cms.security.exception;

/**
 * @author Sanjay Singh Rawat
 */
public class InvalidLoginTypeException extends BadRequestException {

    public InvalidLoginTypeException() {
        super("Invalid login type");
    }

    public InvalidLoginTypeException(String detail) {
        super(detail);
    }
}
