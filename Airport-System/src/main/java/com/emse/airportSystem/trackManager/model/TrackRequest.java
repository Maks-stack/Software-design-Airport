package com.emse.airportSystem.trackManager.model;

import com.emse.airportSystem.planeManager.model.Plane;

public class TrackRequest {
    private Plane plane;

    public TrackRequest(Plane plane){
        this.plane = plane;

        System.out.println("Plane track request created");
    }

    public Plane getPlane() {
        return plane;
    }

}
