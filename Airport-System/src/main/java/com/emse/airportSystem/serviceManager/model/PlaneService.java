package com.emse.airportSystem.serviceManager.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.emse.airportSystem.serviceManager.service.ServiceManager;

public class PlaneService implements Runnable{
    String name;
    String id;
    Boolean available;
    ServiceManager serviceManager;
    Boolean cancelled = false;
    LocalDateTime timeStarted;
    long duration = 50000;
    String planeId = "";

    public void cancelService() {
        setAvailable();
        planeId = "";
        this.cancelled = Boolean.TRUE;
        serviceManager.notifyObservers(this);
        return;
    }

    public void carryOutService() {
        this.available = Boolean.FALSE;
        timeStarted = LocalDateTime.now();
        serviceManager.notifyObservers(this);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.available = Boolean.TRUE;
        planeId = "";
        serviceManager.notifyObservers(this);
    }

    public void setAvailable() {
        this.available = Boolean.TRUE;
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
    public void setPlaneId(String id) {
    	this.planeId = id;
    }
    public String getPlaneId() {
    	return this.planeId;
    }
    
    @Override
    public void run() {
        this.carryOutService();
    }

    public Boolean getCancelled(){ return this.cancelled; }

}
