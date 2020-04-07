package com.emse.airportSystem;

import com.emse.airportSystem.exceptions.ServiceNotAvailableException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
class GlobalControllerExceptionHandler {
    @ExceptionHandler(ServiceNotAvailableException.class)
    public ResponseEntity<?> handleServiceNotAvailableException(Exception ex) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}