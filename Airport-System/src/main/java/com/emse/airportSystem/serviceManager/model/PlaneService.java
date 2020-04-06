package com.emse.airportSystem.serviceManager.model;

public interface PlaneService extends Runnable{

    public  void carryOutService();
    public  void setAvailable();
    public  void setNotAvailable();
    public  String getName();
    public  Boolean getAvailable();



}
