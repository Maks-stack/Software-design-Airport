package com.emse.airportSystem;

import com.emse.airportSystem.plane.PlanesObserver;
import com.emse.airportSystem.planeManager.service.impl.PlaneManager;
import com.emse.airportSystem.serviceManager.service.ServiceManager;
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
	PlaneManager planeManager;

	@Autowired
	TrackManager trackManager;

	@Autowired
	ServicesObserver servicesObserver;

	@Autowired
	PlanesObserver planesObserver;

	@Autowired
	TracksObserver tracksObserver;

	@EventListener(ApplicationReadyEvent.class)
	private void setUpObservers() {
		serviceManager.register(servicesObserver);
		planeManager.register(planesObserver);
		trackManager.register(tracksObserver);
		testContext.runTest();
	}

	public static void main(String Args[]) {
        SpringApplication.run(App.class, Args);
    }
}