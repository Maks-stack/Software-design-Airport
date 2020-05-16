package com.emse.airportSystem.serviceManager.service;

import com.emse.airportSystem.exceptions.ServiceNotAvailableException;
import com.emse.airportSystem.exceptions.RequestNotFoundException;
import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.service.impl.PlaneManager;
import com.emse.airportSystem.planeManager.states.InAir;
import com.emse.airportSystem.serviceManager.model.PlaneService;
import com.emse.airportSystem.serviceManager.model.ServiceBus;
import com.emse.airportSystem.serviceManager.model.ServiceRefuel;
import com.emse.airportSystem.serviceManager.model.ServiceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceManager implements Observable{

    @Autowired
    PlaneManager planeManager;
    Map<String, Object> services  = new HashMap<>();
    Map<String, ServiceRequest> newServiceRequests = new HashMap<>();
    List<ServiceRequest> serviceRequestsInProgress = new ArrayList<ServiceRequest>();
    List<ServiceRequest> serviceRequestsCompleted = new ArrayList<ServiceRequest>();
    List<Observer> observers = new ArrayList<Observer>();
    Map<String,String> serviceCatalog = new HashMap<>();

    {
        serviceCatalog.put("bus", "Bus service"); 
        serviceCatalog.put("refuel", "Refuel service");

        for (int i = 0; i < 10; i++) {
            String name = "Bus "  + i;
            String id = name.replace(" ","").toLowerCase();
            services.put(id, new ServiceBus(name, id , this));
            name = "Refuel " + i;
            id = name.replace(" ","").toLowerCase();
            services.put(id, new ServiceRefuel(name, id, this));
        }
    }

    public List<PlaneService> getServicesByType(String serviceType) {
        List<PlaneService> returnList = new ArrayList<PlaneService>();
        for (Map.Entry<String, Object> entry : services.entrySet()) {
            if (entry.getKey().toLowerCase().startsWith(serviceType.toLowerCase())){
                returnList.add((PlaneService)entry.getValue());
            }
        }
        returnList.sort(Comparator.comparing(PlaneService::getName));
        return returnList;
    }

    public Map<Map.Entry<String, String>, List<PlaneService>> getAllServicesMap(){
        Map<Map.Entry<String, String>, List<PlaneService>> allServices = new HashMap<>();
        Map<String, String> serviceCatalog = getServiceCatalog();

        for (Map.Entry<String, String> entry : serviceCatalog.entrySet()) {
            allServices.put(entry, getServicesByType(entry.getKey()));
        }
        return allServices;
    }

    public void assignService(String requestId, String serviceId) throws ServiceNotAvailableException, RequestNotFoundException {
        PlaneService service = (PlaneService) services.get(serviceId);
        if (service.getAvailable()) {
            registerServiceRequestsInProgress(requestId);
            ServiceRequest originalServiceRequest = serviceRequestsInProgress.stream()
            .filter(item -> item.getId().equals(requestId)).findFirst().orElse(null);
            service.setPlaneId(originalServiceRequest.getPlane().getPlaneId());
            Thread t = new Thread(service);
            t.start();
            planeManager.handleServiceAssigned(originalServiceRequest.getPlane(), service);
        } else {
            throw new ServiceNotAvailableException("Service " +serviceId+ " is not available");
        }
    }

    public void cancelService(String serviceId) {
        PlaneService service = (PlaneService) services.get(serviceId);
        service.cancelService();
    }

    public void assignRandomService() throws ServiceNotAvailableException, RequestNotFoundException {
        try{
            Plane mockPlane = new Plane("Mock1",new InAir(),"Mock1");
            ServiceRequest serviceRequest = new ServiceRequest(mockPlane,"Bus", this);
            newServiceRequests.put(serviceRequest.getId(), serviceRequest);
            this.assignService(serviceRequest.getId(), "bus"+new Random().nextInt(10));
        } catch (Exception e) {
            throw e;
        }
    }

    public void registerServiceRequestsInProgress(String requestId) throws RequestNotFoundException {
        ServiceRequest newServiceInProgress = newServiceRequests.get(requestId);
        if (newServiceInProgress != null){
            serviceRequestsInProgress.add(newServiceInProgress);
            newServiceRequests.remove(newServiceInProgress.getId());
        }else{
            throw new RequestNotFoundException(requestId);
        }
    }

    public void registerNewRequest(Plane plane, String ServiceName){
        ServiceRequest serviceRequest = new ServiceRequest(plane, ServiceName, this);
        newServiceRequests.put(serviceRequest.getId(), serviceRequest);
    }
    
    public void AddServiceTeam2(String serviceId, String serviceName) throws ServiceNotAvailableException, RequestNotFoundException {
    	String ServiceId = serviceId.toLowerCase().split(" ")[0];
    	List<PlaneService> ServiceList = getServicesByType(ServiceId);
    	int IdCounter = -1;
    	for (PlaneService service :ServiceList) {
    		if ( IdCounter+1 != Integer.parseInt(service.getName().split(" ")[1])) {
    			IdCounter++;
    			break;
    		}
    		IdCounter = Integer.parseInt(service.getName().split(" ")[1]);
    	}
    	System.out.println((IdCounter+1 == ServiceList.size()));
    	IdCounter = (IdCounter+1 == ServiceList.size()) ? IdCounter+1 : IdCounter;
    	System.out.println("IdCounter: "+IdCounter +"| Size: "+ ServiceList.size());
    	
    	
    	String name = ServiceId.substring(0, 1).toUpperCase() + ServiceId.substring(1)+" "
    	+ IdCounter;
        String id = name.replace(" ","").toLowerCase();
        PlaneService service = null;//miro
    	switch(ServiceId){
    	case "refuel":
    		service = new ServiceRefuel(name, id , this);
    		break;
    	case "bus":
    		service = new ServiceBus(name, id , this);
    		break;
    	}
    	services.put(id, service);
    	notifyObservers(service);
    	
    }
    

	public void AddServiceTeam(String serviceId, String serviceName) throws ServiceNotAvailableException, RequestNotFoundException {    	
    	int idNumber = 0;
        List<PlaneService> ListSameTypeServices = getServicesByType(serviceId);
        List<Integer> ListofId = new ArrayList<Integer>();
        for (PlaneService Service : ListSameTypeServices) {
        	ListofId.add(Integer.parseInt(Service.getId().replaceAll("[^0-9]", "")));
        }
        for (PlaneService Service : ListSameTypeServices) {
        	if(!ListofId.contains(idNumber)) {
        		System.out.println("Break");
        		break;
        	}
        	idNumber++;
         }
        String name = serviceName;
        String id = serviceId + String.valueOf(idNumber);
        PlaneService service = null;
    	switch(serviceId){
    	case "refuel":
    		service = new ServiceRefuel(name, id , this);
    		break;
    	case "bus":
    		service = new ServiceBus(name, id , this);
    		break;
    	default:
    		System.err.println("ERROR");
    	}
    	services.put(id, service); 
    	notifyObservers(service);
    }
    
    public void RemoveServiceTeam(String serviceId, String teamId) throws ServiceNotAvailableException, RequestNotFoundException {
    	String ServiceId = serviceId.toLowerCase().split(" ")[0];
    	List<PlaneService> ServiceList = getServicesByType(ServiceId);
    	boolean Does_exist = false;
    	for (PlaneService service :ServiceList) {
    		if(service.getName().equals(teamId)) {
    			services.remove(service.getId());
    			
    		}
    	}
    	
    	if(Does_exist) {
    		throw new ServiceNotAvailableException("Service " +serviceId+ " is not available");
    	}else {
    		notifyObservers(null);//mirar mañana 	
    	}
    	
    }

    public void notifyServiceCompleted(PlaneService service){
        planeManager.handleServiceCompleted(service);
    }

    public Collection<ServiceRequest> getNewServiceRequests(){
        return newServiceRequests.values();
    }

    public List<ServiceRequest> getServiceRequestsInProgress(){ return serviceRequestsInProgress; }

    public Map<String, String> getServiceCatalog() { return serviceCatalog; }

    @Override
    public void registerObserver(Observer obj) {
        observers.add(obj);
    }
    @Override
    public void notifyObservers() {
        observers.forEach(obj -> obj.update());
    }

    @Override
    public void notifyObservers(Object obj) {
        observers.forEach(observer -> observer.update(obj));
    }

    @Override
    public void notifyRequestObservers(Object obj) {

    }

    public void notifyServiceRequestObservers(Object obj) {
        observers.forEach(observer -> observer.update(obj));
    }


}
