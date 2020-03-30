package com.emse.Airport_System.ServiceManager;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import com.emse.Airport_System.ServiceManager.ServiceManagerView.ServiceManagerView;
import com.emse.Airport_System.model.Plane;

public class ServiceManagerController {
	
	private static ServiceManagerView theView;
	
	public static enum ServiceEnum{
	    REFUEL, CLEANING
	}

	private static ServiceManagerController firstInstance = null;
	
	Service service = new Service();
	
	private static List<ServiceEnum> reqServices;
			
	private ServiceManagerController() {
		
		theView = new ServiceManagerView();
		theView.addButtonListeners(new ServiceListener());
		theView.setVisible(true);
	}
	
	public static ServiceManagerController getInstance() {
		
		if(firstInstance == null) {
			firstInstance = new ServiceManagerController();
			reqServices = new ArrayList<ServiceEnum>();
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
	
	public static void RequestService(Plane plane, ServiceEnum reqService) {
		reqServices.add(reqService);
		theView.AddService(plane, reqService);
	}
	
	public static void AssignService(Plane plane, Service service) {
		plane.assignService(service);
		service.CarryOutService(plane);
		
	}
	
	class ServiceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			e.getSource();
			System.out.println(e.paramString() + "     " + theView.GetSelectedValue());
		}
		
	}
	
	
}
