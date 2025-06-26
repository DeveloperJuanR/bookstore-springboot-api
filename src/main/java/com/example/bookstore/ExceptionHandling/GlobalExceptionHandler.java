package com.example.bookstore.ExceptionHandling;

import com.example.bookstore.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getReason(), ex.getStatus().value());
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatus().value()));
    }
}
