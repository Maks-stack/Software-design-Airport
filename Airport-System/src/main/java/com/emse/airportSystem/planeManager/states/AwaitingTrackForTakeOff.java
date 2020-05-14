package com.emse.airportSystem.planeManager.states;

import com.emse.airportSystem.planeManager.model.Plane;

public class AwaitingTrackForTakeOff implements State {

	private String state = "Awaiting Track For Take Off";

	public String getStateName(){
		return this.getClass().getSimpleName();
	}

	@Override
	public void proceedToNextState(Plane plane) {
		plane.setState(new TakingOff());
	}

	@Override
	public String getDisplayName() {
		return this.state;
	}
}
