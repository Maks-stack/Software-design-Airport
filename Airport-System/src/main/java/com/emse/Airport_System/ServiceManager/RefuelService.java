package com.emse.Airport_System.ServiceManager;

public class RefuelService extends Service {
	
	private final float RefuelServiceDuration = 5;
	
	public RefuelService() {
		
		ServiceName = "RefuelService";
		duration = RefuelServiceDuration;	
		
		System.out.println("Created Service" + super.ServiceName);
	
	}
}
