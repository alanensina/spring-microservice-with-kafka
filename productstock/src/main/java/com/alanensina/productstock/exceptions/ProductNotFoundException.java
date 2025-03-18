package com.alanensina.productstock.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends RuntimeException{

    final HttpStatus statusCode;

    public ProductNotFoundException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
