package com.inditex.similar.controller;

import com.inditex.similar.client.ProductApiClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ProductApiClient.ProductNotFoundException.class)
    public ResponseEntity<Void> notFound(ProductApiClient.ProductNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }
}
