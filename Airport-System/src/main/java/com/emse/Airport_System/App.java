package com.emse.Airport_System;

import com.emse.Airport_System.ServiceManager.ServiceManager;
import com.emse.Airport_System.ServiceManager.ServicesObserver;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class App {
	@Autowired
	TestContext testContext;

	@Autowired
	ServiceManager serviceManager;

	@Autowired
	ServicesObserver servicesObserver;

	@EventListener(ApplicationReadyEvent.class)
	private void setUpObservers() {
		serviceManager.register(servicesObserver);

		//I have put this here as quick workaround for running test, but it requires starting spring app
		testContext.runTest();
	}

	public static void main(String Args[]) {
        SpringApplication.run(App.class, Args);
    }
}