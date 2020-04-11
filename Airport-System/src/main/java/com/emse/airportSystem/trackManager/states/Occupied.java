package com.emse.airportSystem.trackManager.states;

public class Occupied extends TrackState {
    public Occupied() {
        boolean isAvailable = true;
        String state = "occupied";
    }

    public TrackState proceedToNextStep() {
        System.out.println("Next Step: " + this.getState());
        return new Available();
    }
}
