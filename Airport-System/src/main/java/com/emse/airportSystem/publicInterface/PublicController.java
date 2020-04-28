package com.emse.airportSystem.publicInterface;

import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.service.impl.PlaneManager;
import com.emse.airportSystem.serviceManager.model.PlaneService;
import com.emse.airportSystem.serviceManager.model.ServiceRequest;
import com.emse.airportSystem.serviceManager.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class PublicController {
    @Autowired
    private PlaneManager planeManager;

    public PublicController() {}

    @RequestMapping("/publicinterface")
    public String index(Model model) {
        ArrayList<Plane> planes = planeManager.getPlanes();

        model.addAttribute("allPlanes", planes);

        return "planeManager";
    }

}
