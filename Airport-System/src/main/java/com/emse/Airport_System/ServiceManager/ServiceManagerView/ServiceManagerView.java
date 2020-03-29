package com.emse.Airport_System.ServiceManager.ServiceManagerView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ServiceManagerView extends JFrame {
	
	private JButton requestRefuelServiceButton = new JButton("Request Refuel");    
	private JButton requestCleaningService = new JButton("Request Cleaning");    
	
	public ServiceManagerView() {
		
    	JPanel panel = new JPanel();
    	
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	this.setSize(600,200);
    	this.setTitle("ServiceManager");
    	
    	panel.add(requestRefuelServiceButton);
    	panel.add(requestCleaningService);
    	this.add(panel);	    
	    			
	}
	
	public void addButtonListeners(ActionListener listener) {
		
		requestRefuelServiceButton.addActionListener(listener);
		requestCleaningService.addActionListener(listener);	
	}
}
