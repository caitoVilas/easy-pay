package com.pp.commonsservice.exceptions;

/**
 * Custom exception class for handling token-related errors in the application.
 * This exception is thrown when there are issues related to authentication tokens,
 * such as invalid or expired tokens.
 *
 * @author caito
 *
 */
public class TokenException extends RuntimeException{
    public TokenException(String message) {
        super(message);
    }
}
