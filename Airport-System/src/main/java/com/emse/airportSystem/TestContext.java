package com.emse.airportSystem;
import com.emse.airportSystem.planeManager.states.InAir;
import com.emse.airportSystem.serviceManager.service.ServiceManager;
import com.emse.airportSystem.trackManager.states.Available;
import com.emse.airportSystem.trackManager.model.Track;
import com.emse.airportSystem.trackManager.service.TrackManager;
import com.emse.airportSystem.trackManager.states.TrackState;
import com.emse.airportSystem.planeManager.model.Plane;
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
            this.trackManager.assignTrack();
        }
    }
}
