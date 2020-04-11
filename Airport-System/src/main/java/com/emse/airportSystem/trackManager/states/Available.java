package com.emse.airportSystem.trackManager.states;

public class Available extends TrackState {

    public Available() {
        this.setAvailable(true);
        this.setState("available");
    }

    public TrackState proceedToNextStep() {
        System.out.println("Next Step: " + this.getState());
        return new Assigned();
    }
}
