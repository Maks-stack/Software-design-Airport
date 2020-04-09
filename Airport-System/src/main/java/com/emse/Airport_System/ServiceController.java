package com.emse.Airport_System;

import com.emse.Airport_System.Plane.Plane;
import com.emse.Airport_System.Service.Service;
import com.emse.Airport_System.Service.ServiceManager;
import com.emse.Airport_System.Service.ServiceRequest;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class ServiceController {
    @RequestMapping("/servicemanager")
    public String index(Model model) {
        List<? extends Service> gateServices = ServiceManager.getInstance().getGateServices();
        List<? extends Service> refuelServices = ServiceManager.getInstance().getRefuelServices();
        List<ServiceRequest> newServiceRequests = ServiceManager.getInstance().getNewServiceRequests();
        model.addAttribute("gateServices", gateServices);
        model.addAttribute("refuelServices", refuelServices);
        model.addAttribute("newServiceRequests", newServiceRequests);
        return "serviceManager";
    }

    @RequestMapping("/mockplanerequest.html")
    public void mockPlaneRequest(Model model){
        System.out.println("Mocking plane request");
        Plane plane = new Plane("Test"+System.currentTimeMillis(), "Test");
        String[] optionsArray = {"Gate", "Refuel"};
    }


}
