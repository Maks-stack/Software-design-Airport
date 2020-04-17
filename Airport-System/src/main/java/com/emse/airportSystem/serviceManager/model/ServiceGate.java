package com.emse.airportSystem.serviceManager.model;




import java.time.LocalDateTime;

import com.emse.airportSystem.serviceManager.service.ServiceManager;

public class ServiceGate extends PlaneService {

    public ServiceGate(String name, String id, ServiceManager serviceManager){
        this.available = Boolean.TRUE;
        this.serviceManager = serviceManager;
        this.name = name;
        this.id = id;
        this.cancelled = Boolean.FALSE;
        this.timeCreated = LocalDateTime.now();
        this.duration = 30000;
    }

    public ServiceGate(String name, String id, ServiceManager serviceManager, Boolean available){
        this.available = available;
        this.serviceManager = serviceManager;
        this.name = name;
        this.id = id;
        this.cancelled = Boolean.FALSE;
        this.timeCreated = LocalDateTime.now();
        this.duration = 10000;
    }
}
