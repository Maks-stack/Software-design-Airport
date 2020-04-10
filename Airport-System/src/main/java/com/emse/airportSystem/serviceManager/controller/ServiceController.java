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
import org.springframework.web.bind.annotation.RequestParam;

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
        List<ServiceRequest> serviceRequestsInProgress = SM.getServiceRequestsInProgress();
        model.addAttribute("gateServices", gateServices);
        model.addAttribute("refuelServices", refuelServices);
        model.addAttribute("newServiceRequests", newServiceRequests);
        model.addAttribute("serviceRequestsInProgress", serviceRequestsInProgress);
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
    public ResponseEntity<?> assignservice(@RequestParam String PlaneID, @RequestParam String ServiceRequested, @RequestParam String ServiceSelected  ){
        //Test
        System.out.println(PlaneID);
        System.out.println(ServiceRequested);
        System.out.println(ServiceSelected);

        ServiceSelected = ServiceSelected.replaceAll("\\s+","").toLowerCase();

        try{
            SM.assignService(ServiceSelected);
            SM.registerServiceRequestsInProgress(PlaneID, ServiceRequested);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<ServiceNotAvailableException>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping("/mockassignservice")
    @ResponseBody
    public ResponseEntity<?> mockAssignservice(){
        try{
            SM.assignRandomService();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<ServiceNotAvailableException>(HttpStatus.CONFLICT);
        }
    }
    
    /*@RequestMapping("/cancelService")
    @ResponseBody
    public ResponseEntity<?> assignservice() throws ServiceNotAvailableException{
        SM.assignRandomService();
        return new ResponseEntity<>(HttpStatus.OK);
    }*/


    public void notifyServiceSubscribers() {
        this.template.convertAndSend("/services/updates", "Test");
    }

    public void notifyServiceSubscribers(Object obj) {
        this.template.convertAndSend("/services/updates", obj);

    }


}
