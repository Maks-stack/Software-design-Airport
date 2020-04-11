package com.emse.airportSystem.serviceManager.model;


import com.emse.airportSystem.serviceManager.service.ServiceManager;

public class ServiceGate implements PlaneService {
    String name;
    Boolean available;
    ServiceManager serviceManager;
    Boolean cancelled = false;

    public ServiceGate(String name, ServiceManager serviceManager){
        this.available = Boolean.TRUE;
        this.serviceManager = serviceManager;
        this.name = name;
        this.cancelled = Boolean.FALSE;
    }

    public ServiceGate(String name, ServiceManager serviceManager, Boolean available){
        this.available = available;
        this.serviceManager = serviceManager;
        this.name = name;
        this.cancelled = Boolean.FALSE;
    }

    @Override
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
    
    @Override
    public void cancelService() {
    	setAvailable();
    	this.cancelled = Boolean.TRUE;
        serviceManager.notifyObservers(this);
        return;
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
