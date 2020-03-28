package com.emse.Airport_System.ServiceManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.emse.Airport_System.MockPlane.Plane;

public class ServiceManagerController {
	
	public static enum ServiceEnum{
	    REFUEL, CLEANING
	}

	private static ServiceManagerController firstInstance = null;
	
	Service service = new Service();
			
	private ServiceManagerController() {}
	
	public static ServiceManagerController getInstance() {
		
		if(firstInstance == null) {
			firstInstance = new ServiceManagerController();
		}
		
		return firstInstance;
	}
	
	public static void GetServices(List<ServiceEnum> RequestedServices) {
	
		List<Service> returnServices = new ArrayList<Service>();
		for(ServiceEnum reqService : RequestedServices) {
			if(reqService == ServiceEnum.CLEANING) {
				returnServices.add(new CleaningService());
			}
			if(reqService == ServiceEnum.REFUEL) {
				returnServices.add(new RefuelService());
			}
		}
	}
	
	public static void AssignService(Plane plane, Service service) {
		plane.AssignService(service);
		service.CarryOutService(plane);
	}
	
	
}
