package com.emse.Airport_System.ServiceManager;

public class RefuelService extends Service {
	
	private String ServiceName = "RefuelService";
	
	public void CarryOutService() {
		super.CarryOutService();
		
		System.out.println("Carry out service " + ServiceName);
	}
	
}
