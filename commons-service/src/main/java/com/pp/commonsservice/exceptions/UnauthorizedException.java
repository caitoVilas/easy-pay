package com.pp.commonsservice.exceptions;

/**
 * Custom exception class for handling unauthorized access errors in the application.
 * This exception is thrown when a user attempts to access a resource without proper authorization.
 *
 * @author caito
 *
 */
public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
