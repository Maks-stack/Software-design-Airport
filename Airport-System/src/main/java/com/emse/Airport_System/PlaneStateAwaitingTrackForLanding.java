package com.emse.Airport_System;

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
