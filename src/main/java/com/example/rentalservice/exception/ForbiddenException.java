package com.example.rentalservice.exception;

public class ForbiddenException extends RuntimeException{
    private String message;

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(ExceptionEnums exceptionEnums) {
        super(exceptionEnums.getMessage());
    }

}
