package com.emse.Airport_System.ServiceManager.ServiceManagerView;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ServiceManagerView {
	 @RequestMapping("/ServiceManager")
	    public String ServiceManagerView() {
		 
		 	
		 	
		 
	        return "ServiceManager.jsp";
	    } 
}
