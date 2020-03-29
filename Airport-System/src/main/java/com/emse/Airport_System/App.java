package com.emse.Airport_System;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.emse.Airport_System.ServiceManager.CleaningService;
import com.emse.Airport_System.ServiceManager.RefuelService;
import com.emse.Airport_System.ServiceManager.ServiceManagerController;

import com.emse.Airport_System.MockPlane.Plane;

@SpringBootApplication
public class App {
	
	private static List<Plane> PlaneList = new ArrayList<Plane>();

	
	
	public static  void main(String Args[]) {
		
    	// line added by Luis to test commit from Eclipse.....
        //SpringApplication.run(App.class, Args);
		
        PlaneList.add(new Plane());		
		
		for(Plane currentPlane : PlaneList) {
			ServiceManagerController.getInstance().GetServices(currentPlane.GetRequestedServices());
			ServiceManagerController.getInstance().AssignService(currentPlane, new CleaningService());
		}
    }	
}

