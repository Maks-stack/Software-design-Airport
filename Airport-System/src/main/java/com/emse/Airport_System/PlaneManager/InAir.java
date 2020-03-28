package com.emse.Airport_System.PlaneManager;

public class InAir implements State {
	
	public void proceedToNextState(StateContext context) {
		context.state = new AwaitingTrackForLanding();
	}
}
