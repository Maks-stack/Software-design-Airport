package com.emse.Airport_System;

import java.util.ArrayList;
import java.util.List;

import com.emse.Airport_System.PlaneManager.states.InAir;
import com.emse.Airport_System.model.Plane;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.emse.Airport_System.ServiceManager.CleaningService;
import com.emse.Airport_System.ServiceManager.RefuelService;
import com.emse.Airport_System.ServiceManager.ServiceManagerController;
import com.emse.Airport_System.ServiceManager.ServiceManagerController.ServiceEnum;

@SpringBootApplication
public class App {
	
	private static List<Plane> PlaneList = new ArrayList<Plane>();
	
	
	public static  void main(String Args[]) {
    	// line added by Luis to test commit from Eclipse.....
        //SpringApplication.run(App.class, Args);

		PlaneList.add(new Plane("NEW YORK - MADRID", new InAir()));
		PlaneList.add(new Plane("LONDON - MADRID", new InAir()));
		
		for(Plane currentPlane : PlaneList) {
			ServiceManagerController.getInstance().RequestService(currentPlane, ServiceEnum.REFUEL);
			ServiceManagerController.getInstance().RequestService(currentPlane, ServiceEnum.CLEANING);
		}
    }
}