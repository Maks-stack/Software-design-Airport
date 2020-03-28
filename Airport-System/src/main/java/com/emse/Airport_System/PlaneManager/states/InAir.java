package com.emse.Airport_System.PlaneManager.states;

public class InAir implements State {
	
	public State proceedToNextState() {
		return new AwaitingTrackForLanding();
	}
}
