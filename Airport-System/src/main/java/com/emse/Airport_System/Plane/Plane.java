package com.emse.Airport_System.Plane;

import com.emse.Airport_System.Observable;
import com.emse.Airport_System.Observer;

import java.util.ArrayList;
import java.util.List;

public class Plane implements Observable {

    private String planeId;
    private String planeModel;
    private PlaneState _currentState;
    private List<Observer> observers;
    private final Object MUTEX= new Object();


    public Plane(String id, String model){
        observers=new ArrayList<>();
        planeId = id;
        planeModel = model;
        _currentState = new PlaneStateNew();
    }

    public Plane(String id, String model, PlaneState state){
        observers=new ArrayList<>();
        planeId = id;
        planeModel = model;
        _currentState = state;
    }


    public void setState(PlaneState state){
        this._currentState=state;
        this.notifyObservers();
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

    @Override
    public void register(Observer obj) {
        if(obj == null) throw new NullPointerException("Null Observer");
        synchronized (MUTEX) {
            if(!observers.contains(obj)) observers.add(obj);
        }
    }

    @Override
    public void unregister(Observer obj) {
        synchronized (MUTEX) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObservers() {
        observers.forEach(obj -> obj.update());
    }

    public String getPlaneId() {
        return planeId;
    }
}
