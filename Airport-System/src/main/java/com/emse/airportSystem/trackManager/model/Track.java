package com.emse.airportSystem.trackManager.model;

import com.emse.airportSystem.trackManager.states.TrackState;

public class Track {

    private int trackID;
    private TrackState state;

    public Track(){}

    public Track (int trackID, TrackState state) {
        this.trackID = trackID;
        this.state = state;
    }

    public int getTrackID() {
        return trackID;
    }

    public void setTrackID(int trackID) {
        this.trackID = trackID;
    }

    public TrackState getState() {
        return state;
    }

    public void setState(TrackState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackID=" + trackID +
                ", state=" + state +
                '}';
    }
}
