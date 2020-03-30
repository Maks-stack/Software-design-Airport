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

	JButton assignRefuelService = new JButton("Assign Refuel Service");
	JButton assignCleaningService = new JButton("Assign Cleaning Service");
    
	public ServiceManagerView() {
		
    	JPanel panel = new JPanel();
    	JLabel label = new JLabel("Requested Services");
        
    	
    	//create the model and add elements
       
        //create the list
    	
        ServiceList = new JList<>(listModel);
        
        JScrollPane pane = new JScrollPane(ServiceList);
        
        panel.add(label);
        
        panel.setLayout(new BorderLayout());
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
        this.setSize(400,300);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
    	
    	this.setTitle("ServiceManager");
    	
    	panel.add(pane, BorderLayout.NORTH);
    	panel.add(assignRefuelService, BorderLayout.EAST);
        panel.add(assignCleaningService, BorderLayout.WEST);
        
    	this.add(panel);	
	}
	
	public void AddService(Plane plane, ServiceEnum service) {

        listModel.addElement("Flight: " + plane.getModel() + " Requested Service: " + service.toString());
        this.repaint();
	}
	
	public void addButtonListeners(ActionListener listener) {
		assignRefuelService.addActionListener(listener);
		assignCleaningService.addActionListener(listener);
	}
	
	public String GetSelectedValue() {
		return ServiceList.getSelectedValue();
	}
}
