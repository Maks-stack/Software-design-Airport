package com.emse.Airport_System;

import java.util.ArrayList;

public class ServiceManager {
	
	ArrayList<Service> services;
	ArrayList<Service> availableRefuel;

	public ServiceManager() {
		
		services = new ArrayList<Service>();
		services.add(new ReFuel());
		services.add(new Gate());
		
		availableRefuel = new ArrayList<Service>();
		
	}
	
	public void assignService(Plane plane) {
		
		for(Service s : plane.getServices()) {
			s.carryOutService();
		}
		
		plane.clearOutServices();
	}
	
}
