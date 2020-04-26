package com.emse.airportSystem.plane;

import com.emse.airportSystem.exceptions.ServiceNotAvailableException;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.service.impl.PlaneManager;
import com.emse.airportSystem.planeManager.states.InAir;
import com.emse.airportSystem.serviceManager.model.PlaneService;
import com.emse.airportSystem.serviceManager.service.ServiceManager;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.core.AbstractDestinationResolvingMessagingTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PlaneController {
    @Autowired
    PlaneManager planeManager;

    @Autowired
    ServiceManager serviceManager;

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping("/plane/authorize")
    public String authorizePlane(){
        //authorization logic happens below.. return exception or redirect
        return "redirect:panel";
    }

    @RequestMapping("/plane/panel")
    public String plane(Model model){
        //check for authorization token. If has - all good, if not, redirect to authorize.

        //currently hardcoded values, but this will need to be changed
        String planeId = "Test"+System.currentTimeMillis();
        planeManager.addPlane("A320", new InAir(), planeId);
        model.addAttribute("planeObj", planeId);
        return "pilotInterface";
    }

    @RequestMapping(value = "/plane/requestservice", method = RequestMethod.POST)
    public void requestService(@RequestBody String req){
        Object obj= JSONValue.parse(req);
        JSONObject jsonObject = (JSONObject) obj;
        try{
            String planeId = jsonObject.get("planeId").toString();
            Plane plane = planeManager.getPlaneById(planeId);
            serviceManager.registerNewRequest(plane, jsonObject.get("service").toString());

        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    @RequestMapping(value = "/plane/requesttrack", method = RequestMethod.POST)
    public void requestTrack(@RequestBody String req){
        //To complete
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
