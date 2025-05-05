package com.pragma.powerup.infrastructure.exception;

public class NoTokenFoundException extends RuntimeException {
    public NoTokenFoundException(String message) {
        super(message);
    }
}
