package com.emse.Airport_System.PlaneManager;

public class Landing implements State {

	public void proceedToNextState(StateContext context) {
		context.state = new Landed();
	}
}
