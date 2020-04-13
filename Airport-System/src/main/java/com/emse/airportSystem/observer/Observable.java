package com.emse.airportSystem.observer;

public interface Observable {

    //methods to register and unregister observers
    public void registerObserver(Observer obj);
    public void unregisterObserver(Observer obj);

    //method to notify observers of change
    public void notifyObservers();
    public void notifyObservers(Object obj);
}
