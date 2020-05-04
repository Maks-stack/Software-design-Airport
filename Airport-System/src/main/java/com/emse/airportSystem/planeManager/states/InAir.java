package com.emse.airportSystem.planeManager.states;

public class InAir implements State {

	public String name;

	public State proceedToNextState() {
		return new AwaitingTrackForLanding();
	}

	public String getName(){
		 this.name = this.getClass().getName();
		 return this.name;
	}
}
