package com.emse.airportSystem.planeManager.service;

import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.states.State;

import java.util.ArrayList;

public interface IPlaneManager {

  void addPlane(String model, State state, String planeId);

  void removePlane(Plane plane);

  ArrayList<Plane> getPlanes();

  void proceedToNextState(Plane plane);

  Plane findPlane(String id);
}
