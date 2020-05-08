package com.emse.airportSystem.trackManager.states;

import com.emse.airportSystem.trackManager.model.Track;

public class Assigned implements TrackState {

    private String state = "assigned";

    public Assigned() {
        this.setState("assigned");
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void proceedToNextStep(Track track) {
        track.setState(new Available());
    }
}
