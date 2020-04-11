package com.emse.airportSystem.exceptions;

public class PlaneNotFoundException extends RuntimeException {

  public PlaneNotFoundException() {
    super("Plane could not be found.");
  }
}