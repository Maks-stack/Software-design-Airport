package com.emse.airportSystem.plane;

import com.emse.airportSystem.exceptions.ServiceNotAvailableException;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.service.impl.PlaneManager;
import com.emse.airportSystem.planeManager.states.*;
import com.emse.airportSystem.serviceManager.model.PlaneService;
import com.emse.airportSystem.serviceManager.model.ServiceRequest;
import com.emse.airportSystem.serviceManager.service.ServiceManager;
import com.emse.airportSystem.serviceManager.service.ServiceManager.*;
import com.emse.airportSystem.trackManager.service.TrackManager;

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
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Controller
public class PlaneController {
    @Autowired
    PlaneManager planeManager;

    @Autowired
    ServiceManager serviceManager;
    
    @Autowired
    TrackManager trackManager;

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping("/plane/authorize")
    public String authorizePlane(){
        //authorization logic happens below.. return exception or redirect
        return "redirect:panel";
    }

    @RequestMapping("/plane/panel")
    public String plane(Model model) throws Exception{
        //check for authorization token. If has - all good, if not, redirect to authorize.

        //currently hardcoded values, but this will need to be changed
        String planeId = "Test"+System.currentTimeMillis();
        planeManager.addPlane("A320", new InAir(), planeId);
        Map<String,String> serviceCatalog = serviceManager.getServiceCatalog();
        Plane p = planeManager.getPlaneById(planeId);
        model.addAttribute("planeState", p.getState().getStateName());
        model.addAttribute("planeId", planeId);
        model.addAttribute("serviceCatalogue", serviceCatalog);
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
            if(jsonObject.get("service").toString().startsWith("bus")) { System.out.println("IF");
            	planeManager.proceedToNextState(plane);
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    @RequestMapping(value = "/plane/requesttrack", method = RequestMethod.POST)
    public void requestTrack(@RequestBody String req){
    	Object obj= JSONValue.parse(req);
        JSONObject jsonObject = (JSONObject) obj;
        try{
            String planeId = jsonObject.get("planeId").toString();
            Plane plane = planeManager.getPlaneById(planeId);
            trackManager.registerNewRequest(plane);
            planeManager.proceedToNextState(plane);
        } catch(Exception e){
            System.out.println(e);
        }
    }
    
    @RequestMapping(value = "/plane/requestlanding", method = RequestMethod.POST)
    public void requestlanding(@RequestBody String req){
    	Object obj= JSONValue.parse(req);
        JSONObject jsonObject = (JSONObject) obj;
        try{
            String planeId = jsonObject.get("planeId").toString();
            Plane plane = planeManager.getPlaneById(planeId);
            State state = plane.getState();
           
            if(state.getStateName().equals("InAir")){
            	
	            trackManager.registerNewRequest(plane);
	            planeManager.proceedToNextState(plane);
            }
           
        } catch(Exception e){
            System.out.println(e);
        }

    }
    
    
    
    @RequestMapping(value = "/plane/requestTakeOff", method = RequestMethod.POST)
    public void requestTakeOff(@RequestBody String req){
    	Object obj= JSONValue.parse(req);
        JSONObject jsonObject = (JSONObject) obj;
        String response= "Not Sent";
        try{
            String planeId = jsonObject.get("planeId").toString();
            Plane plane = planeManager.getPlaneById(planeId);
            State state = plane.getState();
            boolean servicefound = false;
            Collection<ServiceRequest> servicesInProgress = serviceManager.getNewServiceRequests();
            for(ServiceRequest service: servicesInProgress)
            {
            	Plane p = service.getPlane();
            	if(p.getPlaneId().equals(planeId))
            	{
            		servicefound = true;
            		break;
            	}
            		
            }
            
            if(state.getStateName().equals("AtTerminal") && !servicefound){
            	trackManager.registerNewRequest(plane);
            	//response =  "Sent";
            }
            
        } catch(Exception e){
            System.out.println(e);
           // return "Not Sent";
        }
    }

    public void notifyServiceSubscribers() {
        this.template.convertAndSend("/planes/updates", "Test");
    }

    public void notifyServiceSubscribers(Object obj) {
        List objList = (List) obj;
        Plane plane = (Plane) objList.get(0);
        //String nomService = (String) objList.get(1);
        //PlaneService service = (PlaneService) objList.get(2);
        this.template.convertAndSend("/planes/"+plane.getPlaneId() +"/updates", obj); System.out.println("LA21");
    }
    
    @RequestMapping("/plane/changeState")
    public void changeState(@RequestBody String req){
    	 Object obj= JSONValue.parse(req);
         JSONObject jsonObject = (JSONObject) obj;
         try{
             String planeId = jsonObject.get("planeId").toString();
             Plane plane = planeManager.getPlaneById(planeId);
             planeManager.proceedToNextState(plane);

         } catch(Exception e){
             System.out.println(e);
         }
    }


}

