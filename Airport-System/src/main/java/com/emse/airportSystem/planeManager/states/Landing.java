package com.emse.airportSystem.planeManager.states;

public class Landing implements State {

	public State proceedToNextState() {
		return new Landed();
	}
}
