package com.emse.Airport_System.ServiceManager;

public abstract class Service {

    boolean Avaliable = false;
    int Duration;

    public Service(){
        System.out.println("Service:" + this.getClass().getName());
    }

    public void CarryOutService(int seconds){
        //Execute action?
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finish");

    }

}
