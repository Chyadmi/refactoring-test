package com.nimbleways.springboilerplate.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;


//i prefer ControllerAdvice for exception handling , provide visible way of doing it
@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException exception) {


        LOGGER.error("Data integrity violation: ", exception);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }



}
