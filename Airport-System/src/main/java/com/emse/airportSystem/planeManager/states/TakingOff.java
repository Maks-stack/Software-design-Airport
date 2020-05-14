package com.emse.airportSystem.planeManager.states;

import com.emse.airportSystem.planeManager.model.Plane;

public class TakingOff implements State {

	private String state = "At terminal";

	public String getStateName(){
		return this.getClass().getSimpleName();
	}

	@Override
	public void proceedToNextState(Plane plane) {
		plane.setState(new InAir());
	}

	@Override
	public String getDisplayName() {
		return this.state;
	}
}
