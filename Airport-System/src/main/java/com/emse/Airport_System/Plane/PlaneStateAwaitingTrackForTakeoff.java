package com.emse.Airport_System.Plane;

public class PlaneStateAwaitingTrackForTakeoff implements PlaneState {
    @Override
    public void proceedToNextState(Plane plane) {
        plane.setState(new PlaneStateTaxiing());
    }

    @Override
    public void proceedToState(Plane plane, PlaneState state) {

    }

    @Override
    public String getStateName(){
        return "Awaiting track to takeoff";
    }
}