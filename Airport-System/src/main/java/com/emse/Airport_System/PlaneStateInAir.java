package com.emse.Airport_System;

public class PlaneStateInAir implements PlaneState {
    @Override
    public void proceedToNextState(Plane plane) {
    }

    @Override
    public void proceedToState(Plane plane, PlaneState state) {
    }

    @Override
    public String getStateName(){
        return "In the air";
    }
}
