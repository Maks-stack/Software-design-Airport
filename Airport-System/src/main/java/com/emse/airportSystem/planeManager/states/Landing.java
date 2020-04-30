package com.emse.airportSystem.planeManager.states;

public class Landing implements State {

	public State proceedToNextState() {
		return new Landed();
	}

	public String getStateName(){
		return this.getClass().getSimpleName();
	}

}
