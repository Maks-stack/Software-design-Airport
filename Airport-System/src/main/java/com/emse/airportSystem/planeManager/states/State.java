package com.emse.airportSystem.planeManager.states;

import com.emse.airportSystem.planeManager.model.Plane;

public interface State {

	default String getStateName(){
		return this.getClass().getName();
	}

	void proceedToNextState(Plane plane);

	String getDisplayName();
}