package com.emse.airportSystem.planeManager.states;

public class Landed implements State {

	public State proceedToNextState() {
		return new AwaitingGateAssigment();
	}

	public String getStateName(){
		return this.getClass().getName();
	}
}
