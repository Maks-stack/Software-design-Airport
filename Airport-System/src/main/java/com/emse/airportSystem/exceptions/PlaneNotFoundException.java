package com.emse.airportSystem.exceptions;

public class PlaneNotFoundException extends Exception{
        public PlaneNotFoundException(String planeId) {
            super("Plane " +planeId+ " was not found");
        }
}
