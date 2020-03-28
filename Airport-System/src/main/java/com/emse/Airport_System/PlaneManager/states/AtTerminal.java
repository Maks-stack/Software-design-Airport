package com.emse.Airport_System.PlaneManager.states;

import com.emse.Airport_System.PlaneManager.states.AwaitingTrackForTakeOff;
import com.emse.Airport_System.PlaneManager.states.State;

public class AtTerminal implements State {

	public State proceedToNextState() {
			return new AwaitingTrackForTakeOff();
	}

}
