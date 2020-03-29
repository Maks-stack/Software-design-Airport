package com.emse.Airport_System;

import com.emse.Airport_System.Plane.PlaneManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	public static void main(String Args[]) {
    	// line added by Luis to test commit from Eclipse.....
        SpringApplication.run(App.class, Args);
        PlaneManager.mainloop();
	}
}