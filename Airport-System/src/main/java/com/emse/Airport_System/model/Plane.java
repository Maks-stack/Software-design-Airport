package com.emse.Airport_System.model;

import com.emse.Airport_System.PlaneManager.states.State;

import java.util.ArrayList;

public class Plane {
  private String planeId;
  private State state;
  private String model;

  public Plane(String model, State state, String planeId){
    this.planeId = planeId;
    this.model = model;
    this.state = state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public State getState() {
    return state;
  }

  public String getModel() {
    return model;
  }

  public String getPlaneId() {
    return planeId;
  }

}
