package com.emse.airportSystem.serviceManager.model;

import java.time.LocalDateTime;

import com.emse.airportSystem.exceptions.ServiceNotAvailableException;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.serviceManager.service.ServiceManager;

public class PlaneService implements Runnable{
    String name;
    String id;
    Boolean available;
    ServiceManager serviceManager;
    LocalDateTime timeStarted;
    long duration = 50000;
    Plane assignedPlane = null;
    Thread serviceThread = null;

    public void cancelService() {
        assignedPlane.removeAssignedService(this.id);
        serviceThread.interrupt();
        setAvailable();
    }

    public void assignRequest(ServiceRequest serviceRequest) throws ServiceNotAvailableException {
        if(available){
            assignedPlane = serviceRequest.plane;
            assignedPlane.addAssignedService(this.id);
            serviceThread = new Thread(this);
            serviceThread.start();
        } else {
            throw new ServiceNotAvailableException("Service "+ name + " is not available");
        }
    }

    public void carryOutService() {
        System.out.println(assignedPlane.toString());
        this.available = Boolean.FALSE;
        timeStarted = LocalDateTime.now();
        serviceManager.notifyObservers(this);
        try {
            Thread.sleep(duration);
            assignedPlane.removeAssignedService(this.id);
            serviceManager.notifyServiceCompleted(this, assignedPlane.getId());
            setAvailable();
        } catch (InterruptedException e) {
            System.out.println("Service cancelled");
        }
    }

    public void setAvailable() {
        this.available = Boolean.TRUE;
        this.assignedPlane = null;
        serviceManager.notifyObservers(this);
    }

    public void setNotAvailable() {
        this.available = Boolean.FALSE;
    }

    public String getName() {
        return this.name;
    }

    public String getId() { return this.id; }

    public Boolean getAvailable() {
        return this.available;
    }

    public LocalDateTime getTimeStarted() {
        return this.timeStarted;
    }

    public long getDuration() {
        return this.duration;
    }

    public Plane getAssignedPlane(){ return assignedPlane; }

    @Override
    public void run() {
        this.carryOutService();
    }

}
