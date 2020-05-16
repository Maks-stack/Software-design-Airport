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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class ServiceController {
    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private SimpMessagingTemplate template;

    public ServiceController(){
    }

    @RequestMapping("/")
	  public String central() {
	  	return "centralControl";
	  }

    @RequestMapping("/servicemanager")
    public String index(Model model) {
        Map<Map.Entry<String, String>, List<PlaneService>> allServices = serviceManager.getAllServicesMap();
        Collection<ServiceRequest> newServiceRequests = serviceManager.getNewServiceRequests();
        List<ServiceRequest> serviceRequestsInProgress = serviceManager.getServiceRequestsInProgress();
        model.addAttribute("allServices", allServices);
        model.addAttribute("newServiceRequests", newServiceRequests);
        model.addAttribute("serviceRequestsInProgress", serviceRequestsInProgress);
        return "serviceManager";
    }
    
    @RequestMapping("/serviceteammanager")
    public String indexTeamManager(Model model) {
        Map<Map.Entry<String, String>, List<PlaneService>> allServices = serviceManager.getAllServicesMap();
        model.addAttribute("allServices", allServices);
        return "serviceTeamManager";
    }

    @RequestMapping("/mockplanerequest")
    @ResponseBody
    public void mockPlaneRequest(Model model){
        System.out.println("Mocking plane request");
        Plane plane = new Plane("A777", new InAir(), "Test"+System.currentTimeMillis());
        String[] optionsArray = {"Bus", "Refuel"};
        serviceManager.registerNewRequest(plane, optionsArray[(new Random()).nextInt(optionsArray.length)]);
    }

    @RequestMapping("/assignservice")
    @ResponseBody
    public ResponseEntity<?> assignservice(@RequestParam String requestId, @RequestParam String serviceSelected)
    throws ServiceNotAvailableException, RequestNotFoundException {
            serviceManager.assignService(requestId, serviceSelected);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/mockassignservice")
    @ResponseBody
    public ResponseEntity<?> mockAssignservice() throws ServiceNotAvailableException, RequestNotFoundException {
            serviceManager.assignRandomService();
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/cancelService")
    @ResponseBody
    public ResponseEntity<?> cancelService(@RequestParam String serviceId) {
        serviceManager.cancelService(serviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/getservicecatalog")
    @ResponseBody
    public Map<String, String> getServiceCatalog(){
        return serviceManager.getServiceCatalog();
    }
    
    @RequestMapping("/addserviceteam")
    @ResponseBody
    public ResponseEntity<?> AddServiceTeam(@RequestParam String serviceSelected, @RequestParam String serviceName)
    throws ServiceNotAvailableException, RequestNotFoundException {
            serviceManager.AddServiceTeam(serviceSelected, serviceName);
            return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping("/removeserviceteam")
    @ResponseBody
    public ResponseEntity<?> RemoveServiceTeam(@RequestParam String serviceSelected, @RequestParam String teamSelected)
    throws ServiceNotAvailableException, RequestNotFoundException {
    		serviceManager.RemoveServiceTeam(serviceSelected, teamSelected);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    public void notifyServiceSubscribers() {
        this.template.convertAndSend("/services/updates", "Test");
    }

    public void notifyServiceSubscribers(Object updatedService) {
        Map<Map.Entry<String, String>, List<PlaneService>> allServices = serviceManager.getAllServicesMap();

        this.template.convertAndSend("/services/updates", Arrays.asList(updatedService, allServices));
    }

    public void notifyServiceRequestSubscribers() {
        this.template.convertAndSend("/services/requestupdates", "Test");
    }

    public void notifyServiceRequestSubscribers(Object updatedService) {
        this.template.convertAndSend("/services/requestupdates", serviceManager.getServiceRequestsInProgress());
    }
}
