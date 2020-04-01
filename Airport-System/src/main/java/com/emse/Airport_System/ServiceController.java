package com.emse.Airport_System;

import com.emse.Airport_System.Plane.Plane;
import com.emse.Airport_System.Service.PlaneService;
import com.emse.Airport_System.Service.ServiceManager;
import com.emse.Airport_System.Service.ServiceRefuel;
import com.emse.Airport_System.Service.ServiceRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
public class ServiceController {
    @RequestMapping("/servicemanager")
    public String index(Model model) {
        List<? extends PlaneService> gateServices = ServiceManager.getInstance().getGateServices();
        List<? extends PlaneService> refuelServices = ServiceManager.getInstance().getRefuelServices();
        List<ServiceRequest> newServiceRequests = ServiceManager.getInstance().getNewServiceRequests();
        model.addAttribute("gateServices", gateServices);
        model.addAttribute("refuelServices", refuelServices);
        model.addAttribute("newServiceRequests", newServiceRequests);
        return "serviceManager";
    }

    @RequestMapping("/getrefuelservices")
    public @ResponseBody List<List<String>> getrefuelservices(Model model){
        List<List<String>> result = new ArrayList<>();
        List<ServiceRefuel> refuelservices = ServiceManager.getInstance().getRefuelServices();
        for (ServiceRefuel sr : refuelservices){
            result.add(Arrays.asList(sr.getName(), sr.getAvailable().toString()));
        }
        return result;
    }

    @RequestMapping("/mockplanerequest.html")
    public void mockPlaneRequest(Model model){
        System.out.println("Mocking plane request");
        Plane plane = new Plane("Test"+System.currentTimeMillis(), "Test");
        String[] optionsArray = {"Gate", "Refuel"};
        ServiceManager SM = ServiceManager.getInstance();
        SM.registerNewRequest(plane, optionsArray[(new Random()).nextInt(optionsArray.length)]);
    }


}
