package com.emse.airportSystem.web;

import com.emse.airportSystem.exceptions.PlaneNotFoundException;
import com.emse.airportSystem.exceptions.ServiceNotAvailableException;

import com.emse.airportSystem.web.model.ApiError;
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

    @ExceptionHandler(PlaneNotFoundException.class)
    public ResponseEntity<ApiError> handlePlaneNotFoundException(Exception ex) {
        return handleExceptions(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ApiError> handleExceptions(String description, HttpStatus status) {
        ApiError error = new ApiError()
            .code(String.valueOf(status.value()))
            .message(status.getReasonPhrase())
            .description(description);

        return new ResponseEntity<>(error, status);
    }

}