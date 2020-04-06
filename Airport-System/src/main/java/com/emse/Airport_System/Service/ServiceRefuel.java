package com.emse.Airport_System.Service;

public class ServiceRefuel implements PlaneService {
    String name;
    Boolean available;

    public ServiceRefuel(String name){
            this.available = Boolean.TRUE;
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


    @Override
    public void run() {

    }
}
