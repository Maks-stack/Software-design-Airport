package com.emse.Airport_System.Service;


import javax.annotation.Resource;

public class ServiceGate implements PlaneService {
    String name;
    Boolean available;
    ServiceManager serviceManager;

    public ServiceGate(String name, ServiceManager serviceManager){
        this.available = Boolean.TRUE;
        this.serviceManager = serviceManager;
        this.name = name;
    }

    public ServiceGate(String name, ServiceManager serviceManager, Boolean available){
        this.available = available;
        this.serviceManager = serviceManager;
        this.name = name;
    }

    @Override
    public void carryOutService() {
        this.available = Boolean.FALSE;
        serviceManager.notifyObservers(this);
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.available = Boolean.TRUE;
        serviceManager.notifyObservers(this);
    }

    @Override
    public void setAvailable() {
        this.available = Boolean.TRUE;
    }

    @Override
    public void setNotAvailable() {
        this.available = Boolean.FALSE;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Boolean getAvailable() {
        return this.available;
    }

    @Override
    public void run() {
        this.carryOutService();
    }
}
