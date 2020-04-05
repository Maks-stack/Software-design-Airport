package com.emse.Airport_System.PlaneManager.states;

public class AwaitingTrackForLanding implements State {
	public State proceedToNextState() {
		return new Landing();
	}
}
