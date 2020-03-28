package com.emse.Airport_System.PlaneManager.states;

public class Landing implements State {

	public State proceedToNextState() {
		return new Landed();
	}
}
