package com.emse.Airport_System.PlaneManager.states;

public class TakingOff implements State {

	public State proceedToNextState() {
		return new InAir();
	}
}
