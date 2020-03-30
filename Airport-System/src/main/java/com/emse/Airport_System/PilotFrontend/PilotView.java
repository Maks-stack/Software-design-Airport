package com.emse.Airport_System.PilotFrontend;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.emse.Airport_System.model.Plane;

public class PilotView extends JFrame { 
		
	private JButton requestRefuelServiceButton = new JButton("Request Refuel");    
	private JButton requestCleaningService = new JButton("Request Cleaning");
	 
	    public PilotView(String Title) {
		 	
	    	JPanel panel = new JPanel();
	    	
	    	Random rand = new Random(); 
	    	int value = rand.nextInt(1000); 
	    	System.out.println(value);
	    	
	    	this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    	
	    	this.setSize(200,200);
	    	this.setTitle(Title);
	    	this.setLocation(value,  200);
	    	
	    	panel.add(requestRefuelServiceButton);
	    	panel.add(requestCleaningService);
	    	this.add(panel);	
	    } 
	    
	    public void addButtonListeners(ActionListener listener) {
			
			requestRefuelServiceButton.addActionListener(listener);
			requestCleaningService.addActionListener(listener);	
		}
}
