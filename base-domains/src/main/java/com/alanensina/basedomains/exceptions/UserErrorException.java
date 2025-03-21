package com.alanensina.basedomains.exceptions;

import org.springframework.http.HttpStatus;

public class UserErrorException extends RuntimeException{

    private final HttpStatus statusCode;

    public UserErrorException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
