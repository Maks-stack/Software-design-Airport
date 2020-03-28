package com.emse.Airport_System.PlaneManager;

public interface State {
	public void proceedToNextState(StateContext context);
}
