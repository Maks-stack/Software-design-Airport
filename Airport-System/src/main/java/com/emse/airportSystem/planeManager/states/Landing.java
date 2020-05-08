package com.emse.airportSystem.planeManager.states;

import com.emse.airportSystem.planeManager.model.Plane;

public class Landing implements State {

	private String state = "Landing";

	public String getStateName(){
		return this.getClass().getSimpleName();
	}

	@Override
	public void proceedToNextState(Plane plane) {
		plane.setState(new Landed());
	}
}
