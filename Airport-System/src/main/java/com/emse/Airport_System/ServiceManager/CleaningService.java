package com.emse.Airport_System.ServiceManager;

import com.emse.Airport_System.ServiceManager.ServiceManagerController.ServiceListener;

public class CleaningService extends Service {
	
	private final float CleaningServiceDuration = 6;
	
	public CleaningService(ServiceEvent event) {
		
		super(event);
		ServiceName = "CleaningService";
		duration = CleaningServiceDuration;
		
		System.out.println("Created Service" + ServiceName);
	}
}
