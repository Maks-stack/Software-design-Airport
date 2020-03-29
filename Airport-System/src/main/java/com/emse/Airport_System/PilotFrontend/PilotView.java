package com.emse.Airport_System.PilotFrontend;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PilotView extends JFrame { 
	
	private JButton requestRefuelServiceButton = new JButton("Request Refuel");    
	private JButton requestCleaningService = new JButton("Request Cleaning");
	 
	    public PilotView() {
		 	
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
