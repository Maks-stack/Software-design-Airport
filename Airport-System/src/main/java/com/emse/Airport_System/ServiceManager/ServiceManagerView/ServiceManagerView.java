package com.emse.Airport_System.ServiceManager.ServiceManagerView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

import com.emse.Airport_System.ServiceManager.Service;
import com.emse.Airport_System.ServiceManager.ServiceManagerController.ServiceEnum;
import com.emse.Airport_System.model.Plane;

public class ServiceManagerView extends JFrame {
	
	private JList<String> ServiceList;
	
	DefaultListModel<String> listModel = new DefaultListModel<>();

    
	public ServiceManagerView() {
		
    	JPanel panel = new JPanel();
    	JLabel label = new JLabel("Requested Services");
        
    	
    	//create the model and add elements
       
        //create the list
    	
        ServiceList = new JList<>(listModel);
        
        add(ServiceList);
        
        panel.add(label);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        this.setSize(600,200);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    	this.setSize(600,200);
    	this.setTitle("ServiceManager");
    	
    	
    	this.add(panel);				
	}
	
	public void AddService(Plane plane, ServiceEnum service) {

        listModel.addElement("Flight: " + plane.getModel() + " Requested Service: " + service.toString());
        this.repaint();
	}
	
	public void addButtonListeners(ActionListener listener) {
		
			
	}
}
