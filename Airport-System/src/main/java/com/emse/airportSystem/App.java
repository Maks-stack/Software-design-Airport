package com.emse.airportSystem;

import com.emse.airportSystem.plane.PlanesObserver;
import com.emse.airportSystem.planeManager.service.impl.PlaneManager;
import com.emse.airportSystem.publicInterface.PublicInterfaceObserver;
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
	private ServiceManager serviceManager;

	@Autowired
	private TrackManager trackManager;

	@Autowired
	private ServicesObserver servicesObserver;

	@Autowired
	PlaneManager planeManager;

	@Autowired
	ServiceRequestsObserver serviceRequestsObserver;

	@Autowired
	TracksObserver tracksObserver;

	@Autowired
	PlanesObserver planesObserver;

	@Autowired
	PublicInterfaceObserver publicInterfaceObserver;

	@EventListener(ApplicationReadyEvent.class)
	private void setUpObservers() {
		serviceManager.registerObserver(servicesObserver);
		serviceManager.registerObserver(serviceRequestsObserver);
		trackManager.registerObserver(tracksObserver);
		planeManager.registerObserver(planesObserver);
		planeManager.registerObserver(publicInterfaceObserver);
	}

	public static void main(String Args[]) {
        SpringApplication.run(App.class, Args);
    }
}
