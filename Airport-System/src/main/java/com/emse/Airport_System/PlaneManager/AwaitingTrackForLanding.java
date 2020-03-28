package com.emse.Airport_System.PlaneManager;

public class AwaitingTrackForLanding implements State {
	public void proceedToNextState(StateContext context) {
		context.state = new Landing();
	}
}
