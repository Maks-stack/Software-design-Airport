package com.emse.Airport_System.PlaneManager.service.impl;

import com.emse.Airport_System.model.Plane;
import com.emse.Airport_System.PlaneManager.states.State;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlaneManager {
	private ArrayList<Plane> planes = new ArrayList<Plane>();

  public void addPlane(String model, State state) {
    this.planes.add(new Plane(model, state));
  }

  public void removePlane(Plane plane) {
    this.planes.remove(plane);
  }

	public ArrayList<Plane> getPlanes() {
		return planes;
	}

	public void proceedToNextState(Plane plane) {
    plane.setState(plane.getState().proceedToNextState());
  }
}
