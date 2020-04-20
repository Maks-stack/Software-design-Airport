package com.emse.airportSystem.planeManager.states;

public class InAir implements State {
	
	public State proceedToNextState() {
		return new AwaitingTrackForLanding();
	}

	public String getStateName(){
		return this.getClass().getName();
	}
}
