package com.emse.airportSystem.planeManager.states;

import com.emse.airportSystem.planeManager.model.Plane;

public class AwaitingTrackForLanding implements State {

	private String state = "Awaiting Track For Landing";

	@Override
	public void proceedToNextState(Plane plane) {
		plane.setState(new Landing());
		System.out.println("AWTTO");
	}

	public String getStateName(){
		return this.getClass().getSimpleName();
		
	}

}
