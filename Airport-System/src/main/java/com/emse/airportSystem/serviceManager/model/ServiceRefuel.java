package com.emse.airportSystem.serviceManager.model;

import java.time.LocalDateTime;

import com.emse.airportSystem.serviceManager.service.ServiceManager;

public class ServiceRefuel extends PlaneService {
    public ServiceRefuel(String name, String id, ServiceManager serviceManager) {
        this.available = Boolean.TRUE;
        this.serviceManager = serviceManager;
        this.name = name;
        this.id = id;
        this.duration = 15000;
        this.timeStarted = LocalDateTime.MAX;
    }

    public ServiceRefuel(String name, String id, ServiceManager serviceManager, Boolean available) {
        this.available = available;
        this.serviceManager = serviceManager;
        this.name = name;
        this.id= id;
        this.duration = 15000;
        this.timeStarted = LocalDateTime.MAX;
    }
}