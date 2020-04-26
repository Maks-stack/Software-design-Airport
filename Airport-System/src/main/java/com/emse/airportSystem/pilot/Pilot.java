package com.emse.airportSystem.pilot;

import org.springframework.beans.factory.annotation.Autowired;

import com.emse.airportSystem.planeManager.service.impl.PlaneManager;
import com.emse.airportSystem.serviceManager.model.PlaneService;
import com.emse.airportSystem.serviceManager.service.ServiceManager;
import com.emse.airportSystem.trackManager.service.TrackManager;

public class Pilot {
	
    private int pilotID; //authentication purposes
    
    @Autowired
    PlaneManager planeManager;
    
    @Autowired
    ServiceManager serviceManager;
    
    @Autowired
    TrackManager trackManager;

    public int getPilotID() {
        return pilotID;
    }

    public void requestTrack(){
        //track request flow
    }

    public boolean requestService(PlaneService service){
    	 boolean request = false;
         return request;
    }
}
