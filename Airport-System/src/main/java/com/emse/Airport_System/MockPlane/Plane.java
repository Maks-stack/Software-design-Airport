package com.emse.Airport_System.MockPlane;

import java.util.ArrayList;
import java.util.List;

import com.emse.Airport_System.ServiceManager.Service;

public class Plane {
	
	private List<Service> ActiveServices;
	
	public Plane() {
		ActiveServices = new ArrayList<Service>();
		System.out.println("create plane");
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
