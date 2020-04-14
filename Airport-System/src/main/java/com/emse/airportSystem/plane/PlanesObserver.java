package com.emse.airportSystem.plane;

import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanesObserver implements Observer {

    private Observable serviceManager;

    private final PlaneController planeController;
    public PlanesObserver(PlaneController planeController){
        this.planeController = planeController;
    }

    @Override
    public void update() {
        this.planeController.notifyServiceSubscribers();
    }

    @Override
    public void update(Object obj) {
        this.planeController.notifyServiceSubscribers(obj);
    }

    @Override
    public void setObservable(Observable serviceManager) {
        this.serviceManager = serviceManager;
    }
}
