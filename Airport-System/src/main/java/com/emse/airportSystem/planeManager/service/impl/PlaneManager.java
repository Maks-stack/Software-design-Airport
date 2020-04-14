package com.emse.airportSystem.planeManager.service.impl;

import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.states.State;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaneManager implements Observable {
    private ArrayList<Plane> planes = new ArrayList<Plane>();
    List<Observer> observers = new ArrayList<Observer>();


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

    public Plane getPlaneById(String planeId) throws Exception
    {
        for (Plane plane: planes){
            if(plane.getPlaneId().equals(planeId)){
                return plane;
            }
        }
        throw new Exception();
    }

    @Override
    public void register(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void unregister(Observer obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(obj -> obj.update());
    }

    @Override
    public void notifyObservers(Object obj) {
        observers.forEach(observer -> observer.update(obj));
    }
}
