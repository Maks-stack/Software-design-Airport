package com.emse.airportSystem.planeManager.states;

public class TakingOff implements State {

	public State proceedToNextState() {
		return new InAir();
	}
}