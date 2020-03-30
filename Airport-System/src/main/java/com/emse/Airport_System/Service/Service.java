package com.emse.Airport_System.Service;

import javax.validation.constraints.Null;

public abstract class Service{

    public abstract void carryOutService();
    public abstract void setAvailable();
    public abstract void setNotAvailable();
    public abstract String getName();
    public abstract Boolean getAvailable();


}
