package com.emse.airportSystem.publicInterface;

import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.service.impl.PlaneManager;
import com.emse.airportSystem.serviceManager.model.PlaneService;
import com.emse.airportSystem.serviceManager.model.ServiceRequest;
import com.emse.airportSystem.serviceManager.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class PublicInterfaceController {
    @Autowired
    private PlaneManager planeManager;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    publicInterfaceData publicInterfaceData;

    public PublicInterfaceController() {}

    @RequestMapping("/publicinterface")
    public String index(Model model) {
        //TODO: Line bellow is a hack, think of a better place/way to mock planes. Maybe not...
        //publicInterfaceData.mockingData();
        ArrayList<Plane> planes = planeManager.getPlanes();
        model.addAttribute("allPlanes", planes);
        return "publicInterface";
    }

    @RequestMapping("/advanceplanestate")
    @ResponseBody
    public void advancePlaneState(){
        publicInterfaceData.advancePlaneState();
    }

    public void notifyServiceSubscribers() {
        this.template.convertAndSend("/planes/updates", "Test");
    }

    public void notifyServiceSubscribers(Object obj) {
        this.template.convertAndSend("/publicinterface/updates", obj);
    }



}
