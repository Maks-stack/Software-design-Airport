package com.emse.Airport_System;

public class PlaneObserver implements Observer{
    private Observable plane;

    @Override
    public void update() {
        System.out.println("plane just got updated");
    }

    @Override
    public void setObservable(Observable plane) {
        this.plane = plane;
    }


}
