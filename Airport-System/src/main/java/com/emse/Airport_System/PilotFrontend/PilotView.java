package com.emse.Airport_System.PilotFrontend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController


public class PilotView {
	 @RequestMapping("/PilotInterface")
	    String PilotView() {
	        return "PilotInterface";
	    } 
}
