package com.emse.Airport_System.model;

import com.emse.Airport_System.PilotFrontend.PilotView;
import com.emse.Airport_System.PlaneManager.states.State;
import com.emse.Airport_System.ServiceManager.Service;

import java.util.ArrayList;

public class Plane {
  private State state;
  private Track track;                    // Plane.track property required to save assigned track
  private String model;
  private ArrayList<Service> services = new ArrayList<Service>();
  private PilotView view;

  public Plane(String model, State state) {
    this.model = model;
    this.state = state;
    this.view = new PilotView();
    this.view.setVisible(true);
  }

  public void setState(State state) {
    this.state = state;
  }

  public State getState() {
    return state;
  }

  public Track getTrack() {
    return track;
  }

  public void setTrack(Track track) {
    this.track = track;
  }

  public String getModel() {
    return model;
  }

  public ArrayList<Service> getServices() {
    return services;
  }

  public void setServices(ArrayList<Service> services) {
    this.services = services;
  }

  public void assignService(Service service) {
    services.add(service);
  }

  public void removeService(Service service) {
    services.remove(service);
  }

}
