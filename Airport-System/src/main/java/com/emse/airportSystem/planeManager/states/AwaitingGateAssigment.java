package com.emse.airportSystem.planeManager.states;

public class AwaitingGateAssigment implements State {
	public State proceedToNextState() {
		return new AtTerminal();
	}

	public String getStateName(){
		return this.getClass().getSimpleName();
	}

}
