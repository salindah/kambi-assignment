package com.test.kambi.exception;

public class BadResponseException extends RuntimeException {

    public BadResponseException(String message){
        super(message);
    }
}
