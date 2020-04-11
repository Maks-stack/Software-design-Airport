package com.emse.airportSystem.trackManager.states;

public abstract class TrackState {

  private boolean isAvailable = true;
  private String state = "available";

  public TrackState() {

  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean available) {
    isAvailable = available;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }

  public abstract TrackState proceedToNextStep();
}
