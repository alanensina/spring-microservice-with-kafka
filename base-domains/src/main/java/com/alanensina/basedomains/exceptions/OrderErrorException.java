package com.alanensina.basedomains.exceptions;

import org.springframework.http.HttpStatus;

public class OrderErrorException extends RuntimeException{

    private final HttpStatus statusCode;

    public OrderErrorException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
