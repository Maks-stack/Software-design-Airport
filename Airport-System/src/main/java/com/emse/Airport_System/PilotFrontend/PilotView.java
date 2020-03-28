package com.emse.Airport_System.PilotFrontend;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emse.Airport_System.Client;

@Controller
public class PilotView {
	 @RequestMapping("/PilotInterface")
	    public String PilotView() {
		 	
		 	Client client = new Client("127.0.0.1", 5000); 
		 
	        return "PilotInterface.jsp";
	    } 
	 
	 public void CreatePlane() {
	 		
		 
	 }
}
