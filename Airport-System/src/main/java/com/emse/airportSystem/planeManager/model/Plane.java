package com.emse.airportSystem.planeManager.model;

import com.emse.airportSystem.planeManager.service.impl.PlaneManager;
import com.emse.airportSystem.planeManager.states.State;
import com.emse.airportSystem.serviceManager.model.PlaneService;
import com.emse.airportSystem.trackManager.model.Track;

import java.util.*;

public class Plane {
  private String id;
  private State state;
  private String model;
  private Integer assignedTrackId = null;
  private List<String> assignedServices = new ArrayList<String>();
  private PlaneManager planeManager;

  public Plane(String model, State state, String id, PlaneManager planeManager){
    this.id = id;
    this.model = model;
    this.state = state;
    this.planeManager = planeManager;
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

  public void nextState() { state.proceedToNextState(this); }

  public String getId() {
    return id;
  }

  public Integer getAssignedTrackId(){ return  assignedTrackId; }

  public void setAssignedTrack(int trackId) {
    this.assignedTrackId = trackId;
    planeManager.notifyObservers(this);
  }

  public void removeAssignedTrack(){
    this.assignedTrackId = null;
    planeManager.notifyObservers(this);
  }

  public List<String> getAssignedServicesIDs() { return assignedServices; }

  public void addAssignedService(String serviceId){
    assignedServices.add(serviceId);
    planeManager.notifyObservers(this);
  }

  public void removeAssignedService(String serviceId){
    assignedServices.remove(serviceId);
    planeManager.notifyObservers(this);
  }

  @Override
  public String toString() {
    return "ID: " + id + " State: " + state + " assigned track: "
            + assignedTrackId;
  }

}
