package com.bekzataitymov.Exception.CustomException;

public class CredentialsExistsException extends RuntimeException{
    private String message;

    public CredentialsExistsException(String message) {
        super(message);
    }
}
