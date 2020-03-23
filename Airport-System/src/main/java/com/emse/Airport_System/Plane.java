package com.emse.Airport_System;

public class Plane {
    private String planeId;
    private String planeModel;
    private PlaneState _currentState;

    public Plane(String id, String model){
        planeId = id;
        planeModel = model;
        _currentState = new PlaneStateNew();
    }

    public Plane(String id, String model, PlaneState state){
        planeId = id;
        planeModel = model;
        _currentState = state;
    }


    public void setState(PlaneState state){
        this._currentState=state;
    }

    public String getId(){
        return this.planeId;
    }

    public String getModel(){
        return this.planeModel;
    }

    public PlaneState getState(){
        return this._currentState;
    }

    public void proceedToNextState(PlaneState state) {
        this._currentState.proceedToNextState(this);
    }

    public void proceedToState(PlaneState state) {
        this._currentState.proceedToState(this, state);
    }




}
