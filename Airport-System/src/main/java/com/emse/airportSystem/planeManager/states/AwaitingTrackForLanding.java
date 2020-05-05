package com.emse.airportSystem.planeManager.states;

public class AwaitingTrackForLanding implements State {

	private String state = "Awaiting Track For Landing";

	public State proceedToNextState() {
		return new Landing();
	}

	public String getStateName(){
		return this.getClass().getSimpleName();
	}

}
