package com.emse.Airport_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	public static void main(String Args[]) {
    	// line added by Luis to test commit from Eclipse.....
        //SpringApplication.run(App.class, Args);
		System.out.println("Start testing ... ");
		Service serviceClean = new Cleaning();
		serviceClean.carryOutService();
		System.out.println("Stop testing ... ");
    }
}