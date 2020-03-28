package com.emse.Airport_System.PlaneManager;

public class AwaitingTrackForTakeOff implements State {
	public void proceedToNextState(StateContext context) {
		context.state = new TakinOff();
	}
}
