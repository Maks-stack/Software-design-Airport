package com.emse.airportSystem.publicInterface;

import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
import com.emse.airportSystem.plane.PlaneController;
import org.springframework.stereotype.Service;

@Service
public class PublicInterfaceObserver implements Observer {

    private Observable planeManager;

    private final PublicInterfaceController publicInterfaceController;

    public PublicInterfaceObserver(PublicInterfaceController publicInterfaceController){
        this.publicInterfaceController = publicInterfaceController;
    }

    @Override
    public void update() {
        this.publicInterfaceController.notifyServiceSubscribers();
    }

    @Override
    public void update(Object obj) {
        this.publicInterfaceController.notifyServiceSubscribers(obj);
    }

    @Override
    public void setObservable(Observable planeManager) {
        this.planeManager = planeManager;
    }
}
