package com.emse.airportSystem.trackManager.model;

import com.emse.airportSystem.planeManager.model.Plane;

import java.util.List;

public class TrackRequest {
    private Plane plane;
    private List<Track> availableTracks;

    public TrackRequest(Plane plane, List<Track> availableTracks){
        this.plane = plane;
        this.availableTracks = availableTracks;

        System.out.println("Plane track request created");
    }

    public Plane getPlane() {
        return plane;
    }

    public List<Track> getAvailableTracks() {
        return availableTracks;
    }
}
