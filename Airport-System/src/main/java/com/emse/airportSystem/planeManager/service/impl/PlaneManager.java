package com.emse.airportSystem.planeManager.service.impl;

import com.emse.airportSystem.exceptions.PlaneNotFoundException;
import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.states.State;
import com.emse.airportSystem.serviceManager.model.PlaneService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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

    //should not be here
    public void proceedToNextState(Plane plane) {
        plane.setState(plane.getState().proceedToNextState());
        notifyObservers(Arrays.asList(plane, null,plane.getState().getStateName()));
    }

    public Plane getPlaneById(String planeId) throws Exception
    {
        for (Plane plane: planes){
            if(plane.getPlaneId().equals(planeId)){
                return plane;
            }
        }
        throw new PlaneNotFoundException(planeId);
    }

    public void handleServiceAssigned(Plane plane, PlaneService service){
        //proceedToNextState(plane); System.out.println("LA1");
        notifyObservers(Arrays.asList(plane, service));
    }

    public void handleServiceCompleted(PlaneService service){
        try {
            notifyObservers(Arrays.asList(getPlaneById(service.getPlaneId()), service)); System.out.println("LA2");
        } catch (Exception e){

        }
    }

    @Override
    public void registerObserver(Observer obj) { observers.add(obj); }

    @Override
    public void notifyObservers() {
        observers.forEach(obj -> obj.update());
    }

    @Override
    public void notifyObservers(Object obj) {
        observers.forEach(observer -> observer.update(obj));
    }
}
