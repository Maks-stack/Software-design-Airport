package com.emse.Airport_System.PilotFrontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController


public class PilotView {
	
	 	@RequestMapping("/PilotInterface")
	    public String PilotView() {
	        return "PilotInterface.jsp";
	    } 
}
