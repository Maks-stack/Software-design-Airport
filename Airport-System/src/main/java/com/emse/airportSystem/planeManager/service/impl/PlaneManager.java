package com.emse.airportSystem.planeManager.service.impl;

import com.emse.airportSystem.exceptions.PlaneNotFoundException;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.service.IPlaneManager;
import com.emse.airportSystem.planeManager.states.InAir;
import com.emse.airportSystem.planeManager.states.State;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class PlaneManager  implements IPlaneManager {
    private ArrayList<Plane> planes = new ArrayList<Plane>();

    {
        for (int i = 0; i<10; i++) {
            planes.add(new Plane("Boeing 737", new InAir(), "Plane " + i));
        }
    }

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

    public Plane getRandomPlane() {
        Random rand = new Random();
        return planes.get(rand.nextInt(planes.size()));
    }
}
