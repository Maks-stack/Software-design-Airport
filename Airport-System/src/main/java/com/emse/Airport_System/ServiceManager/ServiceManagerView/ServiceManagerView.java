package com.emse.Airport_System.ServiceManager.ServiceManagerView;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

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
	
	private JList<PlaneServicePair> ServiceList;
	private JList<Service> ActiveServices;
	
	DefaultListModel<PlaneServicePair> listModel = new DefaultListModel<>();
	DefaultListModel<Service> activeListModel = new DefaultListModel<>();

	JButton assignRefuelService = new JButton("Assign Refuel Service");
	JButton assignCleaningService = new JButton("Assign Cleaning Service");
    
	public ServiceManagerView() {
		
    	JPanel panel = new JPanel();
    	JLabel label = new JLabel("Requested Services");
    	
    	
        
    	//create the model and add elements
       
        //create the list
    	
        ServiceList = new JList<>(listModel);
        ActiveServices = new JList<>(activeListModel);
        
        JScrollPane requestedPane = new JScrollPane(ServiceList);        
        JScrollPane activePane = new JScrollPane(ActiveServices);
        
        panel.setLayout(new FlowLayout());
        
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    	
    	this.setTitle("ServiceManager");
    	
    	JPanel requested = new JPanel();
    	JPanel active = new JPanel();
    	requested.add(requestedPane, BorderLayout.NORTH);
    	active.add(activePane, BorderLayout.NORTH);
    	
    	//panel.add(requestedPane, BorderLayout.NORTH);
    	//panel.add(activePane, BorderLayout.SOUTH);
    	panel.add(assignRefuelService, BorderLayout.EAST);
        panel.add(assignCleaningService, BorderLayout.WEST);
        
    	this.add(panel);
    	this.add(requested);
    	this.add(active);
    	//this.add(panel1);
    	
	}
	
	public void AddService(Plane plane, ServiceEnum service) {

        listModel.addElement(new PlaneServicePair(plane, service));
        this.repaint();
	}
	
	public void addButtonListeners(ActionListener listener) {
		assignRefuelService.addActionListener(listener);
		assignCleaningService.addActionListener(listener);
	}
	
	public PlaneServicePair GetSelectedValue() {
		return ServiceList.getSelectedValue();
	}
	public void UpdateActiveServices(Service service) {
		activeListModel.addElement(service);
	}
}
