package com.emse.airportSystem.publicInterface;

import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
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
        this.publicInterfaceController.notifyPublicInterfaceSubscribers();
    }

    @Override
    public void update(Object obj) {
        this.publicInterfaceController.notifyPublicInterfaceSubscribers(obj);
    }

    @Override
    public void updateRequest(Object obj) {

    }

    @Override
    public void setObservable(Observable planeManager) {
        this.planeManager = planeManager;
    }
}
