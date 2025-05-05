package com.pragma.powerup.domain.exception;

public class AlreadyUserExistException extends RuntimeException {
    public AlreadyUserExistException(String message) {
        super(message);
    }
}


