package com.emse.Airport_System;

public class ServiceGate extends Service {
    String name;

    public ServiceGate(String name){
            this.available = Boolean.TRUE;
            this.name = name;
    }

    @Override
    public void carryOutService() {

    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Boolean getAvailability() {
        return this.available;
    }


}
