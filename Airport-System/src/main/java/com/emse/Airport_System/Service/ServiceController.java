package com.emse.Airport_System.Service;

import com.emse.Airport_System.PlaneManager.states.InAir;
import com.emse.Airport_System.model.Plane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public void mockPlaneRequest(Model model){
        System.out.println("Mocking plane request");
        Plane plane = new Plane("A777", new InAir(), "Test"+System.currentTimeMillis());
        String[] optionsArray = {"Gate", "Refuel"};
        SM.registerNewRequest(plane, optionsArray[(new Random()).nextInt(optionsArray.length)]);
    }

    @RequestMapping("/assignservice")
    public void assignservice(){
        SM.assignRandomService();
    }


    public void notifyServiceSubscribers() {
        this.template.convertAndSend("/services/updates", "Test");
    }


}
