package com.emse.Airport_System.TrackManager.states;

public class TrackState {

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

    public void proceedToNextStep() {
        System.out.println("Current Step: " + state);
    }
}
