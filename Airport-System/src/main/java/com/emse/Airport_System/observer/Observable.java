package com.emse.Airport_System.observer;

public interface Observable {

    //methods to register and unregister observers
    public void register(Observer obj);
    public void unregister(Observer obj);

    //method to notify observers of change
    public void notifyObservers();
    public void notifyObservers(Object obj);
}
