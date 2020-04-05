package com.emse.Airport_System.Service;

import com.emse.Airport_System.Observable;
import com.emse.Airport_System.Observer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicesObserver implements Observer {

    private Observable serviceManager;

    private final ServiceController serviceController;
    public ServicesObserver(ServiceController serviceController){
        this.serviceController = serviceController;
    }

    @Override
    public void update() {
        this.serviceController.notifyServiceSubscribers();
    }

    @Override
    public void update(Object obj) {
        this.serviceController.notifyServiceSubscribers(obj);
    }

    @Override
    public void setObservable(Observable serviceManager) {
        this.serviceManager = serviceManager;
    }
}
