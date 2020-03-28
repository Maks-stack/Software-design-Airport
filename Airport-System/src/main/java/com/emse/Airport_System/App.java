package com.emse.Airport_System;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.emse.Airport_System.ServiceManager.CleaningService;
import com.emse.Airport_System.ServiceManager.RefuelService;
import com.emse.Airport_System.ServiceManager.ServiceManagerController;
import com.emse.Airport_System.MockPlane.Plane;

@SpringBootApplication
public class App {
	
	private static List<Plane> PlaneList = new ArrayList<Plane>();
	private static Server server;
	
	
	public static  void main(String Args[]) {
		
    	// line added by Luis to test commit from Eclipse.....
        SpringApplication.run(App.class, Args);

        PlaneList.add(new Plane());		
        
        server = new Server(5000);
		
		for(Plane currentPlane : PlaneList) {
			ServiceManagerController.getInstance().GetServices(currentPlane.GetRequestedServices());
			ServiceManagerController.getInstance().AssignService(currentPlane, new CleaningService());
		}
    }	
}

