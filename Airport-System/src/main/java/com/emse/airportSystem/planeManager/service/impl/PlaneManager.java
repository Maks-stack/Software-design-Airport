package com.emse.airportSystem.planeManager.service.impl;

import com.emse.airportSystem.exceptions.PlaneNotFoundException;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.service.IPlaneManager;
import com.emse.airportSystem.planeManager.states.State;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PlaneManager  implements IPlaneManager {
    private ArrayList<Plane> planes = new ArrayList<Plane>();

    public void addPlane(String model, State state, String planeId) {
        this.planes.add(new Plane(model, state, planeId));
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

    public Plane findPlane(String id) {
        return planes.stream()
            .filter(plane -> plane.getPlaneId().equals(id))
            .findFirst()
            .orElseThrow(PlaneNotFoundException::new);
    }
}
