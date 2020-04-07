package com.emse.Airport_System.ServiceManager;

import com.emse.Airport_System.ServiceManager.ServiceManagerController.ServiceListener;

public class RefuelService extends Service {
	
	private final float RefuelServiceDuration = 5;
	
	public RefuelService(ServiceEvent event) {
		
		super(event);
		
		ServiceName = "RefuelService";
		duration = RefuelServiceDuration;	
		
		System.out.println("Created Service" + super.ServiceName);
	
	}
}
