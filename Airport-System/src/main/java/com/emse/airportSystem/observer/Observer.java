package com.emse.airportSystem.observer;

public interface Observer {

    //method to update the observer, used by subject
    public void update();
    public void update(Object obj);
    public void updateRequest(Object obj);

    //attach with subject to observe
    public void setObservable(Observable observable);
}