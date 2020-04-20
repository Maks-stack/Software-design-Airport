package com.emse.airportSystem.serviceManager.controller;

import com.emse.airportSystem.exceptions.RequestNotFoundException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

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
        Map<String, List<PlaneService>> allServices = new HashMap<>();
        Map<String, String> serviceCatalog = SM.getServiceCatalog();

        for (Map.Entry<String, String> entry : serviceCatalog.entrySet()) {
            allServices.put(entry.getKey(), SM.getServicesByType(entry.getKey()));
        }

        Collection<ServiceRequest> newServiceRequests = SM.getNewServiceRequests();
        List<ServiceRequest> serviceRequestsInProgress = SM.getServiceRequestsInProgress();
        model.addAttribute("allServices", allServices);
        model.addAttribute("newServiceRequests", newServiceRequests);
        model.addAttribute("serviceRequestsInProgress", serviceRequestsInProgress);
        return "serviceManager";
    }

    @RequestMapping("/mockplanerequest")
    @ResponseBody
    public void mockPlaneRequest(Model model){
        System.out.println("Mocking plane request");
        Plane plane = new Plane("A777", new InAir(), "Test"+System.currentTimeMillis());
        String[] optionsArray = {"Bus", "Refuel"};
        SM.registerNewRequest(plane, optionsArray[(new Random()).nextInt(optionsArray.length)]);
    }

    @RequestMapping("/assignservice")
    @ResponseBody
    public ResponseEntity<?> assignservice(@RequestParam String requestId, @RequestParam String serviceSelected)
    throws ServiceNotAvailableException, RequestNotFoundException {
            SM.assignService(requestId, serviceSelected);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/mockassignservice")
    @ResponseBody
    public ResponseEntity<?> mockAssignservice() throws ServiceNotAvailableException, RequestNotFoundException {
            SM.assignRandomService();
            return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping("/cancelService")
    @ResponseBody
    public ResponseEntity<?> cancelService(@RequestParam String serviceId) {
        SM.cancelService(serviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/getservicecatalog")
    @ResponseBody
    public Map<String, String> getServiceCatalog(){
        return SM.getServiceCatalog();
    }

    public void notifyServiceSubscribers() {
        this.template.convertAndSend("/services/updates", "Test");
    }

    public void notifyServiceSubscribers(Object obj) {
        this.template.convertAndSend("/services/updates", obj);

    }
}
