package com.emse.airportSystem.planeManager.states;

public class AwaitingTrackForLanding implements State {

	public State proceedToNextState() {
		return new Landing();
	}

	public String getStateName(){
		return this.getClass().getName();
	}

}
