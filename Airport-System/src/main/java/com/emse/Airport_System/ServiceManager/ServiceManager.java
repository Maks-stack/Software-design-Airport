package com.emse.Airport_System.ServiceManager;


public class ServiceManager {

	private static ServiceManager firstInstance = null,
	
	Service service = new Service();
			
	private ServiceManager() {}
	
	public static ServiceManager getInstance() {
		
		if(firstInstance == null) {
			firstInstance = new ServiceManager();
		}
		
		return firstInstance;
		
	}

}
