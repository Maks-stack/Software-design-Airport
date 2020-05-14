package com.emse.airportSystem.planeManager.states;

import com.emse.airportSystem.planeManager.model.Plane;

public class AwaitingGateAssigment implements State {


	private String state = "Awaiting Gate Assignment";


	public String getStateName(){
		return this.getClass().getSimpleName();
	}

	@Override
	public void proceedToNextState(Plane plane) {
		plane.setState(new AtTerminal());
	}

	@Override
	public String getDisplayName() {
		return this.state;
	}

}
