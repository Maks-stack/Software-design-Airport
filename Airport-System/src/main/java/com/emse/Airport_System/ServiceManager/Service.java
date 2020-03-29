package com.emse.Airport_System.ServiceManager;

import com.emse.Airport_System.model.Plane;

import java.util.Timer;
import java.util.TimerTask;

public class Service {

	protected float duration;
	public String ServiceName = "";
	
	public Service() {
		
	}
	
	public void CarryOutService(Plane plane) {
		System.out.println("Start Service: " + ServiceName);
		DoCarryOutService(plane);
	}
	
	public void OnServiceFinished(Plane plane) {
		plane.removeService(this);
	}
	
	protected void DoCarryOutService(Plane plane) {
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("Finished task: " + ServiceName);
				OnServiceFinished(plane);
				
			}
		}, (long) (duration*1000));
	}
}
