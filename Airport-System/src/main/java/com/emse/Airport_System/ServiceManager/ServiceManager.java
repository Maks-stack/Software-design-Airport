package com.emse.Airport_System.ServiceManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.emse.Airport_System.MockPlane.Plane;

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
		plane.AssignService(service);
		service.CarryOutService(plane);
	}
	
	
}
