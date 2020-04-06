package com.emse.airportSystem.trackManager.states;

public class Assigned extends TrackState {
    public Assigned() {
        boolean isAvailable = true;
        String state = "assigned";
    }

    public void proceedToNextStep() {
        this.setState("occupied");
        System.out.println("Next Step: " + this.getState());
    }
}
