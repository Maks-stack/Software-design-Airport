package com.emse.Airport_System;
import com.emse.Airport_System.PlaneManager.states.InAir;
import com.emse.Airport_System.ServiceManager.service.ServiceManager;
import com.emse.Airport_System.TrackManager.states.Available;
import com.emse.Airport_System.TrackManager.model.Track;
import com.emse.Airport_System.TrackManager.service.TrackManager;
import com.emse.Airport_System.TrackManager.states.TrackState;
import com.emse.Airport_System.PlaneManager.model.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TestContext {

    @Autowired
    private ServiceManager SM;

    @Autowired
    private TrackManager trackManager;

    private static List<Plane> planeList = new ArrayList<Plane>();
    private static List<Track> trackList = new ArrayList<Track>();

    public void runTest() {
        planeList.add(new Plane("Boeing 777", new InAir(), "plane1"));
        TrackState state = new Available();
        Track track = new Track(374, state);
        trackList.add(track);
        System.out.println(track);

        for (Plane currentPlane : planeList) {
            //We should not pass instance of a service to be assigned to service manager, it is service manager's job to figure out
            //which service to assign. We should pass only the indicator which service is required. Let's discuss this 7th of April
            //serviceManager.assignService(currentPlane, new RefuelService());
            //serviceManager.assignService(currentPlane, new CleaningService());

            this.trackManager.addTrack(track);
            this.trackManager.AssignTrack();
        }
    }
}
