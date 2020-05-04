package com.emse.airportSystem.exceptions;

public class PlaneNotFoundException extends RuntimeException {
        public PlaneNotFoundException(String planeId) {
            super("Plane " +planeId+ " was not found");
        }
}
