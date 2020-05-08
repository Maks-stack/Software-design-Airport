package com.emse.airportSystem.trackManager.states;

import com.emse.airportSystem.trackManager.model.Track;

public class Available implements TrackState {

    private String state = "available";

    public Available() {
        this.setState("available");
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @Override
    public void proceedToNextStep(Track track) {
        track.setState(new Assigned());
    }
}
