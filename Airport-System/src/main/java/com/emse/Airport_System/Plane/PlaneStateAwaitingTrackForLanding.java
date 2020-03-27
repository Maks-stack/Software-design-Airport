package com.emse.Airport_System.Plane;

import com.emse.Airport_System.Plane.Plane;
import com.emse.Airport_System.Plane.PlaneState;

public class PlaneStateAwaitingTrackForLanding implements PlaneState {
    @Override
    public void proceedToNextState(Plane plane) {
    }

    @Override
    public void proceedToState(Plane plane, PlaneState state) {

    }

    @Override
    public String getStateName(){
        return "Awaiting track to land";
    }

}