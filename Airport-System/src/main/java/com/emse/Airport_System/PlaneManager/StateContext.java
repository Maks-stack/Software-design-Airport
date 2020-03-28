package com.emse.Airport_System.PlaneManager;

public class StateContext {
	State state;
	void proceedToNextState() {
		state.proceedToNextState(this);
	}
}
