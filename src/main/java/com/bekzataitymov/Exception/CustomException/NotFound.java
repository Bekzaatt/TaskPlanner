package com.bekzataitymov.Exception.CustomException;

public class NotFound extends RuntimeException{
    private String message;
    public NotFound(String message){
        super(message);
    }
}
