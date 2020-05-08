package com.emse.airportSystem.trackManager.model;

import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.trackManager.states.TrackState;

public class Track {

    private int trackID;
    private TrackState state;
    private Plane assignedPlane;

    public Track() {
    }

    public Track(int trackID, TrackState state) {
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

    public Plane getAssignedPlane() {
        return assignedPlane;
    }

    public void setAssignedPlane(Plane assignedPlane) {
        this.assignedPlane = assignedPlane;
    }

    public void nextState() {
        state.proceedToNextStep(this);
    }

    @Override
    public String toString() {
        return "Track{" +
                "trackID=" + trackID +
                ", state=" + state +
                '}';
    }
}
