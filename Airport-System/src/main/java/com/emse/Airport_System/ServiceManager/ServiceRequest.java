package com.emse.Airport_System.ServiceManager;


import com.emse.Airport_System.model.Plane;

public class ServiceRequest {
    Plane plane;
    String serviceRequested;

    public ServiceRequest(Plane plane, String serviceRequested){
        this.plane = plane;
        this.serviceRequested = serviceRequested;

        System.out.println("Service request created");
    }

    public Plane getPlane() {
        return plane;
    }

    public String getServiceRequested() {
        return serviceRequested;
    }
}