package com.emse.airportSystem.planeManager.states;

public class Landing implements State {

	private String state = "Landing";

	public State proceedToNextState() {
		return new Landed();
	}

	public String getStateName(){
		return this.getClass().getSimpleName();
	}
}
