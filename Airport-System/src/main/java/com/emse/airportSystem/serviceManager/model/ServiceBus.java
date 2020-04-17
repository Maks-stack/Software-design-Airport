package com.emse.airportSystem.serviceManager.model;

import java.time.LocalDateTime;

import com.emse.airportSystem.serviceManager.service.ServiceManager;

public class ServiceBus extends PlaneService {

    public ServiceBus(String name, ServiceManager serviceManager){
        this.available = Boolean.TRUE;
        this.serviceManager = serviceManager;
        this.name = name;
        this.cancelled = Boolean.FALSE;
        this.duration = 20000;
        this.timeStarted = LocalDateTime.MAX;
    }

    public ServiceBus(String name, ServiceManager serviceManager, Boolean available){
        this.available = available;
        this.serviceManager = serviceManager;
        this.name = name;
        this.cancelled = Boolean.FALSE;
        this.duration = 20000;
        this.timeStarted = LocalDateTime.MAX;
    }
}
