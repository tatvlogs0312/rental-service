package com.example.rentalservice.exception;

public class AuthorizationException extends RuntimeException {
    private String message;

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums.getMessage());
    }
}
