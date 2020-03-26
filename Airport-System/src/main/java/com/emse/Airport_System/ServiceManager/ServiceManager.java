package com.emse.Airport_System.ServiceManager;

import com.emse.Airport_System.PlaneManager.Plane;

public final class ServiceManager {
	
    Service[] Services = { new ReFuel(), new Cleaning(), new Gate()
                            , new Luggage(), new Catering()};
    private static ServiceManager Instance = null;

    private ServiceManager(){

    }

    public static ServiceManager getInstance(){
        if (Instance == null) {
            Instance = new ServiceManager();
        }
        return Instance;
    }


    public Service GetFreeService(){

        return null;
    }

    public Service AssignService(Plane plane){

        return null;
    }


}
