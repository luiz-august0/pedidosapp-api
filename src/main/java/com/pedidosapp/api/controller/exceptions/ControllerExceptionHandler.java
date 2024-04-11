package com.pedidosapp.api.controller.exceptions;

import com.pedidosapp.api.service.exceptions.ApplicationGenericsException;
import com.pedidosapp.api.service.exceptions.ValidatorException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ApplicationGenericsException.class)
    public ResponseEntity<StandardError> generics(ApplicationGenericsException e, HttpServletRequest request) {
        String error = e.getClass().getName();
        HttpStatus status = e.getStatus();
        StandardError err = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ValidatorException.class)
    public ResponseEntity<StandardError> generics(ValidatorException e, HttpServletRequest request) {
        String error = e.getClass().getName();
        HttpStatus status = e.getStatus();
        StandardError err = new StandardError(new Date(), status.value(), error, e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}