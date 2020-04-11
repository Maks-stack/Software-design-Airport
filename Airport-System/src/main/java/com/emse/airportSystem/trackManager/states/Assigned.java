package com.emse.airportSystem.trackManager.states;

public class Assigned extends TrackState {
    public Assigned() {
        this.setAvailable(false);
        this.setState("assigned");
    }

    public TrackState proceedToNextStep() {
        System.out.println("Next Step: " + this.getState());
        return new Occupied();
    }
}
