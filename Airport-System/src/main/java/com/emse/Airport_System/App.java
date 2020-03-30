package com.emse.Airport_System;

import java.util.ArrayList;
import java.util.List;

import com.emse.Airport_System.TrackManager.Available;
import com.emse.Airport_System.TrackManager.Track;
import com.emse.Airport_System.TrackManager.TrackManager;
import com.emse.Airport_System.TrackManager.TrackState;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.emse.Airport_System.ServiceManager.CleaningService;
import com.emse.Airport_System.ServiceManager.RefuelService;
import com.emse.Airport_System.ServiceManager.ServiceManager;
import com.emse.Airport_System.MockPlane.Plane;

@SpringBootApplication
public class App {
	
	private static List<Plane> PlaneList = new ArrayList<Plane>();
	private static List<Track> TrackList = new ArrayList<Track>();
	
	public static  void main(String Args[]) {
    	// line added by Luis to test commit from Eclipse.....
        SpringApplication.run(App.class, Args);

		PlaneList.add(new Plane());
		TrackState state = new Available();
		Track track = new Track(374, state);
		TrackList.add(track);
		System.out.println(track);
		
		for(Plane currentPlane : PlaneList) {
			ServiceManager.getInstance().AssignService(currentPlane, new RefuelService());
			ServiceManager.getInstance().AssignService(currentPlane, new CleaningService());

			TrackManager.getInstance().addTrack(track);
			TrackManager.getInstance().AssignTrack(currentPlane);
		}
    }
}