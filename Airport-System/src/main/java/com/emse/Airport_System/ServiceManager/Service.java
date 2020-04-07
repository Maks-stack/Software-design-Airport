package com.emse.Airport_System.ServiceManager;

import com.emse.Airport_System.model.Plane;

import java.util.Timer;
import java.util.TimerTask;

public class Service {

	protected float duration;
	public String ServiceName = "";
	
	private ServiceEvent ie;

    public Service() {
    	
    }
    
	public Service(ServiceEvent event) {
		ie = event; 
	}
	
	public void CarryOutService() {
		System.out.println("Start Service: " + ServiceName);
		DoCarryOutService();
	}
	
	public void OnServiceFinished() {
		 ie.interestingEvent(this);
	}
	
	protected void DoCarryOutService() {
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("Finished task: " + ServiceName);
				OnServiceFinished();
			}
		}, (long) (duration*1000));
	}


    
}
