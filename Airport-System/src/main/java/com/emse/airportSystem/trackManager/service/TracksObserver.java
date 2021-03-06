package com.emse.airportSystem.trackManager.service;

import com.emse.airportSystem.observer.Observer;
import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.trackManager.controller.TrackController;
import org.springframework.stereotype.Service;

@Service
public class TracksObserver implements Observer {

    private Observable trackManager;

    private final TrackController trackController;
    public TracksObserver(TrackController trackController){
        this.trackController = trackController;
    }

    @Override
    public void update() {
        this.trackController.notifyTracksSubscribers();
    }

    @Override
    public void update(Object obj) {
        this.trackController.notifyTracksSubscribers(obj);
    }

    public void updateRequest(Object obj) {
        this.trackController.notifyRequestSubscribers(obj);
    }

    @Override
    public void setObservable(Observable trackManager) {
        this.trackManager = trackManager;
    }
}
