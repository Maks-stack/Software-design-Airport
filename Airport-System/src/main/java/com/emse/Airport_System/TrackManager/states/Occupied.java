package com.emse.Airport_System.TrackManager.states;

public class Occupied extends TrackState {
    public Occupied() {
        boolean isAvailable = true;
        String state = "occupied";
    }

    public void proceedToNextStep() {
        this.setState("available");
        System.out.println("Next Step: " + this.getState());
    }
}
