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
import org.springframework.web.bind.annotation.RequestMapping;

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

    public PublicInterfaceController() {}

    @RequestMapping("/publicinterface")
    public String index(Model model) {
        ArrayList<Plane> planes = planeManager.getPlanes();

        model.addAttribute("allPlanes", planes);

        return "planeManager";
    }

    public void notifyServiceSubscribers() {
        this.template.convertAndSend("/planes/updates", "Test");
    }

    public void notifyServiceSubscribers(Object obj) {
        List objList = (List) obj;
        Plane plane = (Plane) objList.get(0);
        PlaneService service = (PlaneService) objList.get(1);
        this.template.convertAndSend("/planes/"+plane.getPlaneId() +"/updates", obj);
    }



}
