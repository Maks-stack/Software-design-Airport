package com.emse.airportSystem.plane;

import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
import org.springframework.stereotype.Service;

@Service
public class PlanesObserver implements Observer {

    private Observable planeManager;

    private final PlaneController planeController;
    public PlanesObserver(PlaneController planeController){
        this.planeController = planeController;
    }

    @Override
    public void update() {
        this.planeController.notifyPlaneSubscribers();
    }

    @Override
    public void update(Object obj) {
        this.planeController.notifyPlaneSubscribers(obj);
    }

    @Override
    public void updateRequest(Object obj) {

    }

    @Override
    public void setObservable(Observable planeManager) {
        this.planeManager = planeManager;
    }
}
