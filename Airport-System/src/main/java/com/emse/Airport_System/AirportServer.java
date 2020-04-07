package com.emse.Airport_System;
import java.net.*;

import javax.websocket.server.ServerEndpoint;
import java.io.*;


@ServerEndpoint("/endpoint")
public class AirportServer {
	
	public AirportServer() throws IOException {
		
		System.out.println("awdaw");
		
		System.out.println("wainting for client");
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(5555, 0, InetAddress.getByName("localhost"));
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		
		}
		Socket s = null;
		
		try {
			s = ss.accept();				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("d");
			e.printStackTrace(); 
		}		
			
		InputStreamReader in = new InputStreamReader(s.getInputStream());	
		BufferedReader bf = new BufferedReader(in);
		
		String str =  bf.readLine();
		System.out.println("Client: " + str);
	}
}
