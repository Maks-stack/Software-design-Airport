package com.emse.Airport_System.PlaneManager.states;

public class AwaitingTrackForTakeOff implements State {
	public State proceedToNextState() {
		return new TakingOff();
	}
}
