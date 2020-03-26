package com.emse.Airport_System;

import java.util.ArrayList;

public class ServiceManager {

    ArrayList<Service> gateServices = new ArrayList<Service>();

    private static ServiceManager instance = null;
    private ServiceManager() {
        // Exists only to defeat instantiation.
    }

    public static ServiceManager getInstance() {
        if(instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    public void initializeServices(){
        for (int i = 0; i < 10; i++) {
            String name = "Gate"  + i;
            gateServices.add(new ServiceGate(name));
        }
    }

    public void printAllServices(){
        for(Service service : gateServices){
            System.out.printf("Service name: %s; Service availability: %s\n", service.getName(), service.getAvailability().toString());
        }
    }



}
