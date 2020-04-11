package com.emse.airportSystem.trackManager.model;

import com.emse.airportSystem.planeManager.model.Plane;

public class TrackRequest {
    private TrackType trackRequested;
    private Plane plane;

    public TrackRequest(Plane plane, TrackType trackRequested){
        this.plane = plane;
        this.trackRequested = trackRequested;

        System.out.println("Plane track request created");
    }

    public Plane getPlane() {
        return plane;
    }

    public TrackType getTrackRequested() {
        return trackRequested;
    }
}
