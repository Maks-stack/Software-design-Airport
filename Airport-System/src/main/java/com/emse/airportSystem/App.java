package com.emse.airportSystem;

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
	private ServiceManager serviceManager;

	@Autowired
	private TrackManager trackManager;

	@Autowired
	private ServicesObserver servicesObserver;

	@Autowired
	private TracksObserver tracksObserver;

	@EventListener(ApplicationReadyEvent.class)
	private void setUpObservers() {
		serviceManager.register(servicesObserver);
		trackManager.register(tracksObserver);
	}

	public static void main(String Args[]) {
        SpringApplication.run(App.class, Args);
    }
}