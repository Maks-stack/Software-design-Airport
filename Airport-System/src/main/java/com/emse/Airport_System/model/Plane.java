package com.emse.Airport_System.model;

import com.emse.Airport_System.PilotFrontend.PilotView;
import com.emse.Airport_System.PlaneManager.states.State;
import com.emse.Airport_System.ServiceManager.Service;
import com.emse.Airport_System.ServiceManager.ServiceManagerController;
import com.emse.Airport_System.ServiceManager.ServiceManagerController.ServiceEnum;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Plane {
  private State state;
  private Track track;                    // Plane.track property required to save assigned track
  private String model;
  private ArrayList<Service> services = new ArrayList<Service>();
  private PilotView view;

  public Plane(String model, State state) {
    this.model = model;
    this.state = state;
    this.view = new PilotView(model);
    this.view.setVisible(true);
    this.view.addButtonListeners(new ServiceListener(this));
  }

  public void setState(State state) {
    this.state = state;
  }

  public State getState() {
    return state;
  }

  public Track getTrack() {
    return track;
  }

  public void setTrack(Track track) {
    this.track = track;
  }

  public String getModel() {
    return model;
  }

  public ArrayList<Service> getServices() {
    return services;
  }

  public void setServices(ArrayList<Service> services) {
    this.services = services;
  }

  public void assignService(Service service) {
    services.add(service);
  }

  public void removeService(Service service) {
    services.remove(service);
  }
  
  public class ServiceListener implements ActionListener {
	  
	  	Plane thisPlane;
	  	
	  	ServiceListener(Plane callingPlane){
	  		thisPlane = callingPlane;
	  	}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(e.paramString().contains("Refuel")) {
				ServiceManagerController.getInstance().RequestService(thisPlane, ServiceEnum.REFUEL);
			}
			if(e.paramString().contains("Cleaning")) {
				ServiceManagerController.getInstance().RequestService(thisPlane, ServiceEnum.CLEANING);
			}
			System.out.println(e.paramString());
		}	
	}
}
