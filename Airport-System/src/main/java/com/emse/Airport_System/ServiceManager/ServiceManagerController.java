package com.emse.Airport_System.ServiceManager;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emse.Airport_System.ServiceManager.ServiceManagerView.ServiceManagerView;
import com.emse.Airport_System.model.Plane;

public class ServiceManagerController implements ServiceEvent {

	private static ServiceManagerView theView;
	
	public static enum ServiceEnum{
	    REFUEL, CLEANING
	}

	private static ServiceManagerController firstInstance = null;
		
	private static Map<Plane, ArrayList<ServiceEnum>> reqServiceDictionary;
			
	private ServiceManagerController() {
		
		theView = new ServiceManagerView();
		theView.addButtonListeners(new ServiceListener());
		theView.setVisible(true);
	}
	
	public static ServiceManagerController getInstance() {
		
		if(firstInstance == null) {
			firstInstance = new ServiceManagerController();
			reqServiceDictionary = new HashMap<Plane, ArrayList<ServiceEnum>>();
		}
	
		return firstInstance;
	}
	
	public static void RequestService(Plane plane, ServiceEnum reqService) {
		
		if(!reqServiceDictionary.containsKey(plane.getModel())) {
			reqServiceDictionary.put(plane, new ArrayList());
		}
		if(!reqServiceDictionary.get(plane).contains(reqService)) {		
			reqServiceDictionary.get(plane).add(reqService);
		}		
		
		theView.UpdateView(reqServiceDictionary);
	}
	
	public static void AssignService(Plane plane, Service service) {
		
		plane.assignService(service);
		service.CarryOutService();

	}
	
	class ServiceListener implements ActionListener {
				
		@Override
		public void actionPerformed(ActionEvent e) {
			CreateService(theView.GetSelectedValue().service);
		}
	}
	
	public void CreateService(ServiceEnum service) {
		switch (service) {
	        case CLEANING:  AssignService(theView.GetSelectedValue().plane, new CleaningService(this));
	                 break;
	        case REFUEL:  AssignService(theView.GetSelectedValue().plane, new RefuelService(this));
	                 break;
	        default:
	        break;
		}
	}

	@Override
	public void interestingEvent(Service service) {
		System.out.println(service.ServiceName + " is finished in serviceManager");
		for(Plane currentPlane : reqServiceDictionary.keySet()) {
			if(reqServiceDictionary.get(currentPlane).contains(service)) {
				reqServiceDictionary.get(currentPlane).remove(service);
			}
			if(reqServiceDictionary.get(currentPlane).isEmpty()) {
				reqServiceDictionary.remove(currentPlane);
			}
		}
		theView.UpdateView(reqServiceDictionary);
	}

	@Override
	public void interestingEvent() {
		// TODO Auto-generated method stub
		
	}
}
