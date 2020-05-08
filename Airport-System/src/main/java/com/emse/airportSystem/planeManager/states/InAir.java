package com.emse.airportSystem.planeManager.states;

import com.emse.airportSystem.planeManager.model.Plane;

public class InAir implements State {

	private String state = "In Air";

	public String getStateName(){
		return this.getClass().getSimpleName();
	}

	@Override
	public void proceedToNextState(Plane plane) {
		plane.setState(new AwaitingTrackForLanding());
	}
}
