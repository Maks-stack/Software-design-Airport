package com.emse.airportSystem.serviceManager.model;

import java.time.LocalDateTime;

import com.emse.airportSystem.serviceManager.service.ServiceManager;

public class ServiceBus extends PlaneService {

    public ServiceBus(String name,String id, ServiceManager serviceManager){
        this.available = Boolean.TRUE;
        this.serviceManager = serviceManager;
        this.name = name;
        this.id = id;
        this.cancelled = Boolean.FALSE;
        this.cancelled = Boolean.FALSE;
        this.timeStarted = LocalDateTime.MAX;
        this.duration = 30000;
        
    }

    public ServiceBus(String name, String id, ServiceManager serviceManager, Boolean available){
        this.available = available;
        this.serviceManager = serviceManager;
        this.name = name;
        this.id = id;
        this.cancelled = Boolean.FALSE;
        this.duration = 30000;
        this.timeStarted = LocalDateTime.MAX;
    }
}
