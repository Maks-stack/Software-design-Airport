package com.emse.airportSystem.planeManager.states;

import com.emse.airportSystem.planeManager.model.Plane;

public class Landed implements State {

	private String state = "Landed";

	public String getStateName(){
		return this.getClass().getSimpleName();
	}

	@Override
	public void proceedToNextState(Plane plane) {
		plane.setState(new AwaitingGateAssigment());
	}

	@Override
	public String getDisplayName() {
		return this.state;
	}
}
