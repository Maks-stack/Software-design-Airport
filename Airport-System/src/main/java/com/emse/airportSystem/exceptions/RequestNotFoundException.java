package com.emse.airportSystem.exceptions;

public class RequestNotFoundException extends Exception{
    public RequestNotFoundException(String requestId) {
        super("Request " +requestId+ " was not found");
    }
}
