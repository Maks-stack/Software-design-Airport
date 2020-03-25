package com.emse.Airport_System;

import java.util.ArrayList;

public class ServiceManager {
	
	ArrayList<Service> services;
	ArrayList<Service> availableRefuel;

	public ServiceManager() {
		
		services = new ArrayList<Service>();
		availableRefuel = new ArrayList<Service>();
		
	}
	
	public Service getFreeService() {
		Service s = new Service();
		return s;
	}
	
	public Service assignService(Plane plane) {
		Service s = new Service();
		return s;	
	}
	
}
