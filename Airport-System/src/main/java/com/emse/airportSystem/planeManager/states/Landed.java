package com.emse.airportSystem.planeManager.states;

public class Landed implements State {

	private String state = "Landed";

	public State proceedToNextState() {
		return new AwaitingGateAssigment();
	}

	public String getStateName(){
		return this.getClass().getSimpleName();
	}
}
