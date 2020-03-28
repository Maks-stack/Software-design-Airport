package com.emse.Airport_System.PlaneManager;

import java.util.ArrayList;

public class Plane extends StateContext {
	State state = new InAir();
	Track track;										// Plane.track property required to save assigned track
	String model;
	ArrayList<Service> services = new ArrayList<Service>();
	Plane(String model) {
		this.model = model;
	}
	void setState() {
		state.proceedToNextState(this);   				// State.proceedToNextState(StateContext context)
	}
	
	// TrackManager() is a temporal call awating final implement
	void assignTrack() {
		track = new TrackManager().assignTrack(this);	// Plane.track property required to save assigned track
	}
}
