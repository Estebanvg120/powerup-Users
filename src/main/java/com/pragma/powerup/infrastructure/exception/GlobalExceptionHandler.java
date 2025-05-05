package com.pragma.powerup.infrastructure.exception;

import com.pragma.powerup.domain.exception.AlreadyUserExistException;
import com.pragma.powerup.domain.exception.IncorrectPasswordException;
import com.pragma.powerup.domain.exception.InvalidRole;
import com.pragma.powerup.domain.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyUserExistException.class)
    public ResponseEntity<GlobalExceptionResponse> handleJwtError(AlreadyUserExistException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<GlobalExceptionResponse> handleJwtError(IncorrectPasswordException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<GlobalExceptionResponse> handleJwtError(NoDataFoundException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<GlobalExceptionResponse> handleJwtError(ExpiredTokenException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(NoTokenFoundException.class)
    public ResponseEntity<GlobalExceptionResponse> handleJwtError(NoTokenFoundException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidRole.class)
    public ResponseEntity<GlobalExceptionResponse> handleJwtError(InvalidRole ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }


    private ResponseEntity<GlobalExceptionResponse> buildErrorResponse(HttpStatus status, String message, WebRequest request) {
        GlobalExceptionResponse error = new GlobalExceptionResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, status);
    }
}
