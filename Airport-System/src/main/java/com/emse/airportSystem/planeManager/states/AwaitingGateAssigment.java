package com.emse.airportSystem.planeManager.states;

public class AwaitingGateAssigment implements State {
	public State proceedToNextState() {
		return new TakingOff();
	}

	public String getStateName(){
		return this.getClass().getName();
	}

}
