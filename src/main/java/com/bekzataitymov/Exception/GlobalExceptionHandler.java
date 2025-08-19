package com.bekzataitymov.Exception;

import com.bekzataitymov.Exception.CustomException.CredentialsExistsException;
import com.bekzataitymov.Exception.CustomException.NotFound;
import com.bekzataitymov.Exception.CustomException.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFound.class)
    public ResponseEntity<?> notFoundException(NotFound ex){
        return new ResponseEntity<>("message: " + ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CredentialsExistsException.class)
    public ResponseEntity<?> credentialsExistsException(CredentialsExistsException ex){
        return new ResponseEntity<>("message: " + ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalException(Exception ex){
         return new ResponseEntity<>("message: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unauthorizedException(UnauthorizedException ex){
        return new ResponseEntity<>("message: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
