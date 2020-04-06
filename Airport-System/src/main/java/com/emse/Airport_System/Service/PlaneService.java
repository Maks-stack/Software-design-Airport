package com.emse.Airport_System.Service;

import javax.validation.constraints.Null;

public  interface PlaneService extends Runnable{

    public  void carryOutService();
    public  void setAvailable();
    public  void setNotAvailable();
    public  String getName();
    public  Boolean getAvailable();



}
