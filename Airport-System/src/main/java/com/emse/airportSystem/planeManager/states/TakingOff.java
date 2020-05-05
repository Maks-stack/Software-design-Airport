package com.emse.airportSystem.planeManager.states;

public class TakingOff implements State {

	private String state = "At terminal";

	public State proceedToNextState() {
		return new InAir();
	}

	public String getStateName(){
		return this.getClass().getSimpleName();
	}
}
