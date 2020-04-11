package com.emse.airportSystem.exceptions;

public class ServiceNotAvailableException extends Exception{
    public ServiceNotAvailableException(String message) {
        super(message);
    }
}