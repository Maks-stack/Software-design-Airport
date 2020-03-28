package com.emse.Airport_System.MockPlane;

import java.util.ArrayList;
import java.util.List;

import com.emse.Airport_System.ServiceManager.Service;
import com.emse.Airport_System.ServiceManager.ServiceManagerController.ServiceEnum;

public class Plane {
	
	private List<Service> ActiveServices;
	private List<ServiceEnum> RequestedServices;
	
	public Plane() {
		ActiveServices = new ArrayList<Service>();
		System.out.println("create plane");
		RequestedServices = new ArrayList<ServiceEnum>();
		
		RequestedServices.add(ServiceEnum.REFUEL);
		RequestedServices.add(ServiceEnum.CLEANING);
	}
	
	public List<ServiceEnum> GetRequestedServices() {
		return RequestedServices;
	}
	
	public void AssignService(Service service) {
		ActiveServices.add(service);
		
		PrintActiveServices();
		
	}
	public void RemoveService(Service service) {
		ActiveServices.remove(service);
		PrintActiveServices();
	}
	
	private void PrintActiveServices() {
		System.out.println("ActiveServices: " + ActiveServices.toString());
	}
	
}
