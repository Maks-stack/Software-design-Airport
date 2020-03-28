package com.emse.Airport_System.PlaneManager;

public class TakinOff implements State {

	public void proceedToNextState(StateContext context) {
		context.state = new InAir();
	}
}
