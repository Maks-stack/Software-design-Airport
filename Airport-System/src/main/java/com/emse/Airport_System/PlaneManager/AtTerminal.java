package com.emse.Airport_System.PlaneManager;

public class AtTerminal implements State {

	public void proceedToNextState(StateContext context) {
			context.state = new AwaitingTrackForTakeOff();
	}

}
