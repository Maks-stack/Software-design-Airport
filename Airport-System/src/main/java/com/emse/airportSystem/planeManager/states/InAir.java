package com.emse.airportSystem.planeManager.states;

public class InAir implements State {

	private String state = "In Air";

	public State proceedToNextState() {
		return new AwaitingTrackForLanding();
	}

	public String getState() {
		return state;
	}
}
