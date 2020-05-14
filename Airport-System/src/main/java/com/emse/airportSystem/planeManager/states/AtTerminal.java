package com.emse.airportSystem.planeManager.states;

import com.emse.airportSystem.planeManager.model.Plane;

public class AtTerminal implements State {

	private String state = "At terminal";

	public String getStateName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void proceedToNextState(Plane plane) {
		plane.setState(new AwaitingTrackForTakeOff());
	}

	@Override
	public String getDisplayName() {
		return this.state;
	}

}
