package com.emse.airportSystem.planeManager.states;

public class AwaitingTrackForTakeOff implements State {

	private String state = "Awaiting Track For Take Off";

	public State proceedToNextState() {
		return new TakingOff();
	}

	public String getState() {
		return state;
	}
}
