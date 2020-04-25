package com.emse.airportSystem.pilot;

import com.emse.airportSystem.serviceManager.model.PlaneService;

public class Pilot {
    private int pilotID; //authentication purposes

    public int getPilotID() {
        return pilotID;
    }

    public void requestTrack(){
        //track request flow
    }

    public void requestService(PlaneService service){
        //service request flow
    }
}
