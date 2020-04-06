package com.emse.airportSystem.planeManager.states;

public class AtTerminal implements State {

	public State proceedToNextState() {
			return new AwaitingTrackForTakeOff();
	}

}
