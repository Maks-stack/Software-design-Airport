package com.emse.Airport_System;

public interface Observer {

    //method to update the observer, used by subject
    public void update();

    //attach with subject to observe
    public void setObservable(Observable observable);
}