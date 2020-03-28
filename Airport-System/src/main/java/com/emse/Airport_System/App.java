package com.emse.Airport_System;

import java.util.ArrayList;
import java.util.List;

import com.emse.Airport_System.PlaneManager.states.InAir;
import com.emse.Airport_System.model.Plane;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.emse.Airport_System.ServiceManager.CleaningService;
import com.emse.Airport_System.ServiceManager.RefuelService;
import com.emse.Airport_System.ServiceManager.ServiceManager;

@SpringBootApplication
public class App {
	
	private static List<Plane> PlaneList = new ArrayList<Plane>();
	
	
	public static  void main(String Args[]) {
    	// line added by Luis to test commit from Eclipse.....
        SpringApplication.run(App.class, Args);

		PlaneList.add(new Plane("test", new InAir()));
		
		for(Plane currentPlane : PlaneList) {
			ServiceManager.getInstance().AssignService(currentPlane, new RefuelService());
			ServiceManager.getInstance().AssignService(currentPlane, new CleaningService());
		}
    }
}