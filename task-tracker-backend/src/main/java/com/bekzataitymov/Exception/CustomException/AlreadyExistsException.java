package com.bekzataitymov.Exception.CustomException;

public class AlreadyExistsException extends RuntimeException{
    private String message;

    public AlreadyExistsException(String message) {
        super(message);
    }
}
