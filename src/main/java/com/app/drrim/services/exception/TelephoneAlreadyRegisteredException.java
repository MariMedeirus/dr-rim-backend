package com.app.drrim.services.exception;

public class TelephoneAlreadyRegisteredException extends RuntimeException{
    public TelephoneAlreadyRegisteredException(String message) {
        super(message);
    }
}
