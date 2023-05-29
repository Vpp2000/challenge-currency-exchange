package com.vpp97.demo.api.controllers.exception_handler;

import com.vpp97.demo.dto.response.ErrorResponse;
import com.vpp97.demo.exceptions.ElementNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ElementNotFoundControllerAdvice {
    @ExceptionHandler({ElementNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleIfIdNotFound(ElementNotFoundException exception){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message("Element not found")
                .detail(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
