package com.emse.airportSystem;

import com.emse.airportSystem.exceptions.RequestNotAvailableException;
import com.emse.airportSystem.exceptions.ServiceNotAvailableException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(ServiceNotAvailableException.class)
    public ResponseEntity<BackendErrorResponse> handleServiceNotAvailableException(Exception ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(new BackendErrorResponse(ex), responseHeaders, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RequestNotAvailableException.class)
    public ResponseEntity<BackendErrorResponse> handleRequestNotAvailableException(Exception ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(new BackendErrorResponse(ex), responseHeaders, HttpStatus.CONFLICT);
    }

}