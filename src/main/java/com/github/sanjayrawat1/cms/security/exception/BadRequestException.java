package com.github.sanjayrawat1.cms.security.exception;

/**
 * @author Sanjay Singh Rawat
 * @since 2021.07.21
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException() {}

    public BadRequestException(String message) {
        super(message);
    }
}
