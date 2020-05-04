package com.emse.airportSystem.planeManager.states;

public interface State {
	State proceedToNextState();

	default String getStateName(){
		return this.getClass().getName();
	}
}
