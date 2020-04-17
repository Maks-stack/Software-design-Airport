package com.emse.airportSystem.serviceManager.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.emse.airportSystem.serviceManager.service.ServiceManager;

public class PlaneService implements Runnable{
    String name;
    Boolean available;
    ServiceManager serviceManager;
    Boolean cancelled = false;
    LocalDateTime timeStarted;
    long duration = 10000;

    public void cancelService() {
        setAvailable();
        this.cancelled = Boolean.TRUE;
        serviceManager.notifyObservers(this);
        return;
    }

    public void carryOutService() {
        this.available = Boolean.FALSE;
        serviceManager.notifyObservers(this);
        timeStarted = LocalDateTime.now();
        System.out.println("TIMESTARTED: " + timeStarted);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.available = Boolean.TRUE;
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

    public Boolean getAvailable() {
        return this.available;
    }
    
    public LocalDateTime getTimeStarted() {
        return this.timeStarted;
    }

    public long getDuration() {
        return this.duration;
    }
    
    @Override
    public void run() {
        this.carryOutService();
    }

    public Boolean getCancelled(){ return this.cancelled; }

}
