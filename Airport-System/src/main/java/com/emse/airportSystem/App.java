package com.emse.airportSystem;

import com.emse.airportSystem.plane.PlanesObserver;
import com.emse.airportSystem.planeManager.service.impl.PlaneManager;
import com.emse.airportSystem.serviceManager.service.ServiceManager;
import com.emse.airportSystem.serviceManager.service.ServiceRequestsObserver;
import com.emse.airportSystem.serviceManager.service.ServicesObserver;
import com.emse.airportSystem.trackManager.service.TrackManager;
import com.emse.airportSystem.trackManager.service.TracksObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class App {
	@Autowired
	TestContext testContext;

	@Autowired
	ServiceManager serviceManager;

	@Autowired
	TrackManager trackManager;

	@Autowired
	PlaneManager planeManager;

	@Autowired
	ServicesObserver servicesObserver;
	
	@Autowired
	ServiceRequestsObserver serviceRequestsObserver;

	@Autowired
	TracksObserver tracksObserver;

	@Autowired
	PlanesObserver planesObserver;

	@EventListener(ApplicationReadyEvent.class)
	private void setUpObservers() {
		serviceManager.registerObserver(servicesObserver);
		serviceManager.registerObserver(serviceRequestsObserver);
		trackManager.registerObserver(tracksObserver);
		planeManager.registerObserver(planesObserver);

		//I have put this here as quick workaround for running test, but it requires starting spring app
		testContext.runTest();
	}

	public static void main(String Args[]) {
        SpringApplication.run(App.class, Args);
    }
}