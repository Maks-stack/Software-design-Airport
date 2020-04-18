package com.emse.airportSystem.serviceManager.model;


import com.emse.airportSystem.planeManager.model.Plane;

public class ServiceRequest {
    String id;
    Plane plane;
    String serviceRequested;

    public ServiceRequest(Plane plane, String serviceRequested){
        this.id = plane.getPlaneId()+serviceRequested+System.currentTimeMillis();
        this.plane = plane;
        this.serviceRequested = serviceRequested;
    }
    
    public Plane getPlane() {
        return plane;
    }
    public String getServiceRequested() {
        return serviceRequested;
    }
    public String getId(){return this.id;}
}
