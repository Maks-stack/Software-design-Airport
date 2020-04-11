package com.emse.airportSystem.serviceManager.model;

import com.emse.airportSystem.serviceManager.service.ServiceManager;

public class PlaneService implements Runnable{
    String name;
    Boolean available;
    ServiceManager serviceManager;
    Boolean cancelled = false;

    public void cancelService() {
        setAvailable();
        this.cancelled = Boolean.TRUE;
        serviceManager.notifyObservers(this);
        return;
    }

    public void carryOutService() {
        this.available = Boolean.FALSE;
        serviceManager.notifyObservers(this);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.available = Boolean.TRUE;
        serviceManager.notifyObservers(this);
    }

    public void setAvailable() {
        this.available = Boolean.TRUE;
    }

    public void setNotAvailable() {
        this.available = Boolean.FALSE;
    }

    public String getName() {
        return this.name;
    }

    public Boolean getAvailable() {
        return this.available;
    }

    @Override
    public void run() {
        this.carryOutService();
    }

    public Boolean getCancelled(){return this.cancelled;}

}
