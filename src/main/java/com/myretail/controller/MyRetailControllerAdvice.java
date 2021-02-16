package com.myretail.controller;

import com.myretail.exception.CustomException;
import com.myretail.exception.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyRetailControllerAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyRetailControllerAdvice.class);
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Map<String, String>> customExceptionHandler(CustomException ex){
        LOGGER.error(ex.fillInStackTrace().getMessage());
        String message = Error.getMessage(ex.getMessage());
        System.out.println("message in exception "+message);
        Map<String, String> map = new HashMap<>();
        map.put("message", message);
        return new ResponseEntity<>(map, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> validationExceptionHandler(
            MethodArgumentNotValidException ex) {
        LOGGER.error(ex.fillInStackTrace().getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
