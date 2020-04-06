package com.emse.airportSystem.planeManager.states;

public class Landed implements State {

	public State proceedToNextState() {
		return new AtTerminal();
	}

}
