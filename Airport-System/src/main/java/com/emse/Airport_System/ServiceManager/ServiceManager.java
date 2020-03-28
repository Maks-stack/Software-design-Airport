package com.emse.Airport_System.ServiceManager;

import com.emse.Airport_System.model.Plane;

public class ServiceManager {

	private static ServiceManager firstInstance = null;
	
	Service service = new Service();
			
	private ServiceManager() {}
	
	public static ServiceManager getInstance() {
		
		if(firstInstance == null) {
			firstInstance = new ServiceManager();
		}
		
		return firstInstance;
	}
	
	public static void AssignService(Plane plane, Service service) {
		plane.assignService(service);
		service.CarryOutService(plane);
	}
	
	
}
