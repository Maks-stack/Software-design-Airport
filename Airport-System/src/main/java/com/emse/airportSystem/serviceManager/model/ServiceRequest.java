package com.emse.airportSystem.serviceManager.model;


import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.serviceManager.service.ServiceManager;

import net.minidev.json.JSONObject;

public class ServiceRequest {
    String id;
    Plane plane;
    String serviceRequested;
    ServiceManager serviceManager;

    public ServiceRequest(Plane plane, String serviceRequested, ServiceManager serviceManager){
        this.id = plane.getId()+serviceRequested+System.currentTimeMillis();
        this.plane = plane;
        this.serviceRequested = serviceRequested;
        this.serviceManager = serviceManager;

        JSONObject json = new JSONObject();
        json.put("type", "serviceRequest");
        json.put("id", this.id);
        json.put("plane", this.plane);
        json.put("serviceRequested", this.serviceRequested);

        serviceManager.notifyObservers(json);
    }

    public Plane getPlane() {
        return plane;
    }
    public String getServiceRequested() {
        return serviceRequested;
    }
    public String getId(){return this.id;}
}
