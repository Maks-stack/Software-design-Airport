package com.emse.Airport_System.Plane;

public interface PlaneState {
    public void proceedToNextState(Plane plane);
    public void proceedToState(Plane plane, PlaneState state);
    public String getStateName();
}
