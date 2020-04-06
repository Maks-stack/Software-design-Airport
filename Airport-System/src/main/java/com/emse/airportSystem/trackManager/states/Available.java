package com.emse.airportSystem.trackManager.states;

public class Available extends TrackState {

    public Available() {
        boolean isAvailable = true;
        String state = "available";
    }

    public void proceedToNextStep() {
        this.setState("assigned");
        System.out.println("Next Step: " + this.getState());
    }
}
