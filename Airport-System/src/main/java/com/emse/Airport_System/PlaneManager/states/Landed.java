package com.emse.Airport_System.PlaneManager.states;

public class Landed implements State {

	public State proceedToNextState() {
		return new AtTerminal();
	}

}
