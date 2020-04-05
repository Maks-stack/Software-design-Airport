package com.emse.Airport_System;

import com.emse.Airport_System.Service.ServiceManager;
import com.emse.Airport_System.Service.ServicesObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class App {
	public static void main(String Args[]) {
        SpringApplication.run(App.class, Args);
	}

	@Autowired
	ServiceManager SM;
	@Autowired
	ServicesObserver so;

	@EventListener(ApplicationReadyEvent.class)
	public void setUpObservers() {
		SM.register(so);
	}
}