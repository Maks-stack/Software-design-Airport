package com.emse.airportSystem.planeManager.states;

public class AtTerminal implements State {

	private String state = "At terminal";

	public State proceedToNextState() {
			return new AwaitingTrackForTakeOff();
	}

	public String getState() {
		return state;
	}
}
