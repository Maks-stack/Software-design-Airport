package com.emse.Airport_System.Service;

public class ServiceGate extends PlaneService {

    String name;
    Boolean available;

    public ServiceGate(String name){
            this.available = Boolean.TRUE;
            this.name = name;
    }

    public ServiceGate(String name, Boolean available){
        this.available = available;
        this.name = name;
    }

    @Override
    public void carryOutService() {

    }

    @Override
    public void setAvailable() {
        this.available = Boolean.TRUE;
    }

    @Override
    public void setNotAvailable() {
        this.available = Boolean.FALSE;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Boolean getAvailable() {
        return this.available;
    }




}
