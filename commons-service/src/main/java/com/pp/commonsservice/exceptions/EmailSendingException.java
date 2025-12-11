package com.pp.commonsservice.exceptions;

/**
 * Custom exception class for handling email sending errors in the application.
 * This exception is thrown when there are issues encountered while attempting to send an email.
 *
 * @author caito
 *
 */
public class EmailSendingException extends RuntimeException{
    public EmailSendingException(String message) {
        super(message);
    }
}
