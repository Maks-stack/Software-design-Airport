package com.emse.Airport_System.Service;

public abstract class Service{

    public Boolean available;
    public abstract void carryOutService();
    public abstract String getName();
    public abstract Boolean getAvailability();


}
