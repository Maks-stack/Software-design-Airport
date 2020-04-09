package com.emse.airportSystem.serviceManager.controller;

import com.emse.airportSystem.exceptions.ServiceNotAvailableException;
import com.emse.airportSystem.planeManager.states.InAir;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.serviceManager.model.PlaneService;
import com.emse.airportSystem.serviceManager.model.ServiceRequest;
import com.emse.airportSystem.serviceManager.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;

@Controller
public class ServiceController {
    @Autowired
    private ServiceManager SM;

    @Autowired
    private SimpMessagingTemplate template;

    public ServiceController(){
    }

    @RequestMapping("/servicemanager")
    public String index(Model model) {
        List<? extends PlaneService> gateServices = SM.getGateServices();
        List<? extends PlaneService> refuelServices = SM.getRefuelServices();
        List<ServiceRequest> newServiceRequests = SM.getNewServiceRequests();
        model.addAttribute("gateServices", gateServices);
        model.addAttribute("refuelServices", refuelServices);
        model.addAttribute("newServiceRequests", newServiceRequests);
        return "serviceManager";
    }

    @RequestMapping("/mockplanerequest")
    @ResponseBody
    public void mockPlaneRequest(Model model){
        System.out.println("Mocking plane request");
        Plane plane = new Plane("A777", new InAir(), "Test"+System.currentTimeMillis());
        String[] optionsArray = {"Gate", "Refuel"};
        SM.registerNewRequest(plane, optionsArray[(new Random()).nextInt(optionsArray.length)]);
    }

    @RequestMapping("/assignservice")
    @ResponseBody
    public ResponseEntity<?> assignservice() throws ServiceNotAvailableException{
        SM.assignRandomService();
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping("/cancelService")
    @ResponseBody
    public ResponseEntity<?> assignservice() throws ServiceNotAvailableException{
        SM.assignRandomService();
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public void notifyServiceSubscribers() {
        this.template.convertAndSend("/services/updates", "Test");
    }

    public void notifyServiceSubscribers(Object obj) {
        this.template.convertAndSend("/services/updates", obj);

    }


}
