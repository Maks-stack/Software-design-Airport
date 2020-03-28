package com.emse.Airport_System.PlaneManager;

public class Landed implements State {

	public void proceedToNextState(StateContext context) {
		context.state = new AtTerminal();
	}

}
