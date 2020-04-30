package com.emse.airportSystem.planeManager.states;

public interface State {
	State proceedToNextState();
	String getStateName();
}
