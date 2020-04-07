package com.emse.Airport_System.ServiceManager.ServiceManagerView;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.emse.Airport_System.ServiceManager.CleaningService;
import com.emse.Airport_System.ServiceManager.Service;
import com.emse.Airport_System.ServiceManager.ServiceManagerController.ServiceEnum;
import com.emse.Airport_System.model.Plane;

public class ServiceManagerView extends JFrame {
	
	public class PlaneServicePair
	{
		public Plane plane;
		public ServiceEnum service;
		
		PlaneServicePair(Plane _plane, ServiceEnum _service){
			plane = _plane; 
			service = _service;  
		}
	};
	
	private JList<Plane> planeList;
	private JList<PlaneServicePair> ServiceList;
	private JList<Service> ActiveServices;
	
	DefaultListModel<PlaneServicePair> listModel = new DefaultListModel<>();
	DefaultListModel<Service> activeListModel = new DefaultListModel<>();
	DefaultListModel<Plane> planeListModel = new DefaultListModel<>();

	JButton refuelButton = new JButton("Assign Refuel Service");
	JButton cleaningButton = new JButton("Assign Cleaning Service");
    
	public ServiceManagerView() {
		
    	//create the model and add elements
       
        //create the list
    	
    	this.setLayout(new BorderLayout());
    	
        ServiceList = new JList<>(listModel);
        ActiveServices = new JList<>(activeListModel);
        planeList = new JList(planeListModel);
        
        JScrollPane PlaneList = new JScrollPane(planeList); 
        JScrollPane requestedList = new JScrollPane(ServiceList);        
        JScrollPane activeList = new JScrollPane(ActiveServices);
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refuelButton);
        buttonPanel.add(cleaningButton);
        buttonPanel.setBorder(new EmptyBorder(50, 10, 10, 10));
       
        
      
        JPanel ListPanel = new JPanel();
       
        
        JPanel planePanel = new JPanel(new BorderLayout());
        JLabel planeLabel = new JLabel("Requesting Planes");
        planePanel.add(planeLabel, BorderLayout.NORTH);
        planePanel.add(PlaneList, BorderLayout.SOUTH);
        
        JPanel requestedPanel = new JPanel(new BorderLayout());
        JLabel requestedLabel = new JLabel("Requested Services");
        requestedPanel.add(requestedLabel, BorderLayout.NORTH);
        requestedPanel.add(requestedList, BorderLayout.SOUTH);
        
        JPanel activePanel= new JPanel(new BorderLayout());
        JLabel activeLabel = new JLabel("Active Services");
        requestedPanel.add(activeLabel, BorderLayout.NORTH);
        requestedPanel.add(activeList, BorderLayout.SOUTH);
       
        
        ListPanel.add(planePanel);
        ListPanel.add(requestedPanel);
        ListPanel.add(activePanel);
        
        ListPanel.setBorder(new EmptyBorder(50, 10, 10, 10));
       
        this.add(ListPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
            	
    	this.setTitle("ServiceManager");

    	this.pack();
	}
	
	public PlaneServicePair GetSelectedValue() {
		return ServiceList.getSelectedValue();
	}
	
	public void UpdateView(Map<Plane, ArrayList<ServiceEnum>> map) {
		
		for(Plane currentPlane : map.keySet()) {
			planeListModel.removeAllElements(); 
			planeListModel.addElement(currentPlane);
		}
		this.repaint();
	}
	
	public void addButtonListeners(ActionListener listener) {
		refuelButton.addActionListener(listener);
		cleaningButton.addActionListener(listener);
	}
}
