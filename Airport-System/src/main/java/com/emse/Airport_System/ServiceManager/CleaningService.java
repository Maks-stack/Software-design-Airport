package com.emse.Airport_System.ServiceManager;

public class CleaningService extends Service {
	
	private final float CleaningServiceDuration = 6;
	
	public CleaningService() {
		
		ServiceName = "CleaningService";
		duration = CleaningServiceDuration;
		
		System.out.println("Created Service" + ServiceName);
	}
}
