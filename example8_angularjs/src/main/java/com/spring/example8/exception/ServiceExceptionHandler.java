package com.spring.example8.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
