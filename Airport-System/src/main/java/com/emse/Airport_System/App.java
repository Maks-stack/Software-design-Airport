package com.emse.Airport_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	public static void main(String Args[]) {

		System.out.println("Start testing ... ");
		
		//With services
		Plane p = new Plane();
		ServiceManager sm = new ServiceManager();
		sm.assignService(p);
		
		//Without services
		System.out.println("Without services ... ");
		sm.assignService(p);
		
		System.out.println("Stop testing ... ");
    }
}