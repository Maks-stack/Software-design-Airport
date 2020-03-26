package com.emse.Airport_System.ServiceManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.emse.Airport_System.MockPlane.Plane;

public class Service {

	protected float duration;
	public String ServiceName = "";
	
	public Service() {
		
	}
	
	protected void CarryOutService(Plane plane) {
		System.out.println("Start Service: " + ServiceName);
		DoCarryOutService(plane);
	}
	
	public void OnServiceFinished(Plane plane) {
		plane.RemoveService(this);
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
