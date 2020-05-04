package com.emse.airportSystem.serviceManager.service;

import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
import com.emse.airportSystem.serviceManager.controller.ServiceController;
import org.springframework.stereotype.Service;

@Service
public class ServiceRequestsObserver implements Observer {

    private Observable serviceManager;

    private final ServiceController serviceController;
    public ServiceRequestsObserver(ServiceController serviceController){
        this.serviceController = serviceController;
    }

    @Override
    public void update() {
    	
        this.serviceController.notifyServiceRequestSubscribers();
    }

    @Override
    public void update(Object obj) {
        this.serviceController.notifyServiceRequestSubscribers(obj);
    }

    @Override
    public void updateRequest(Object obj) {

    }

    @Override
    public void setObservable(Observable serviceManager) {
        this.serviceManager = serviceManager;
    }
}
