package com.emse.airportSystem.serviceManager.model;

import com.emse.airportSystem.serviceManager.service.ServiceManager;

public class ServiceRefuel extends PlaneService {
    public ServiceRefuel(String name, ServiceManager serviceManager) {
        this.available = Boolean.TRUE;
        this.serviceManager = serviceManager;
        this.name = name;
        this.cancelled = Boolean.FALSE;
    }

    public ServiceRefuel(String name, ServiceManager serviceManager, Boolean available) {
        this.available = available;
        this.serviceManager = serviceManager;
        this.name = name;
        this.cancelled = Boolean.FALSE;
    }
}