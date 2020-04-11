package com.emse.airportSystem.serviceManager.service;

import com.emse.airportSystem.exceptions.ServiceNotAvailableException;
import com.emse.airportSystem.exceptions.RequestNotAvailableException;
import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.serviceManager.model.PlaneService;
import com.emse.airportSystem.serviceManager.model.ServiceGate;
import com.emse.airportSystem.serviceManager.model.ServiceRefuel;
import com.emse.airportSystem.serviceManager.model.ServiceRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceManager implements Observable{
    Map<String, Object> services  = new HashMap<>();
    List<ServiceRequest> newServiceRequests = new ArrayList<ServiceRequest>();
    List<ServiceRequest> serviceRequestsInProgress = new ArrayList<ServiceRequest>();
    List<ServiceRequest> serviceRequestsCompleted = new ArrayList<ServiceRequest>();
    List<Observer> observers = new ArrayList<Observer>();

    List<PlaneService> activeServices = new ArrayList<PlaneService>();
    
    {
        for (int i = 0; i < 10; i++) {
            String name = "Gate "  + i;
            services.put("gate"+i, new ServiceGate(name, this));
            name = "Refuel " + i;
            services.put("refuel"+i, new ServiceRefuel(name));
        }
    }

    public List<ServiceGate> getGateServices() {
        List<ServiceGate> returnList = new ArrayList<ServiceGate>();
        for (Map.Entry<String, Object> entry : services.entrySet()) {
            if (entry.getKey().startsWith("gate")){
                returnList.add((ServiceGate)entry.getValue());
            }
        }
        returnList.sort(Comparator.comparing(ServiceGate::getName));
        return returnList;
    }

    public List<ServiceRefuel> getRefuelServices() {
        List<ServiceRefuel> returnList = new ArrayList<ServiceRefuel>();
        for (Map.Entry<String, Object> entry : services.entrySet()) {
            if (entry.getKey().startsWith("refuel")){
                returnList.add((ServiceRefuel)entry.getValue());
            }
        }
        returnList.sort(Comparator.comparing(ServiceRefuel::getName));
        return returnList;
    }

    public void assignService(String serviceId) throws ServiceNotAvailableException {
        PlaneService service = (PlaneService) services.get(serviceId);
        if (service.getAvailable()) {
            Thread t = new Thread(service);
            t.start();
        } else {
            throw new ServiceNotAvailableException("Service " +serviceId+ " is not available");
        }
        activeServices.add(service);
    }
    
    public void cancelService(String serviceId) {
    	System.out.println("cancel: " + serviceId);
    	for(PlaneService currentService : activeServices) {
    		if(currentService.getName().contains(serviceId)) {
    			currentService.cancelService();
    			activeServices.remove(currentService);
    			break;
    		}
    	}
    }

    public void assignRandomService() throws ServiceNotAvailableException {
        try{
            this.assignService("gate"+new Random().nextInt(10));
        } catch (Exception e) {
            throw e;
        }

    }

    public void registerServiceRequestsInProgress(String PlaneID, String ServiceRequested ) throws RequestNotAvailableException {

        ServiceRequest NewServiceInProgress = null;

        //Detect the request
        for(ServiceRequest service : newServiceRequests) {
            if(service.getServiceRequested().equals(ServiceRequested) && service.getPlane().getPlaneId().equals(PlaneID)) {

                NewServiceInProgress = service;
                break;
            }
        }
        if (NewServiceInProgress != null){

            serviceRequestsInProgress.add(NewServiceInProgress);
            newServiceRequests.remove(NewServiceInProgress);

        }else{
            throw new RequestNotAvailableException("That request is not available");
        }



    }

    public void registerNewRequest(Plane plane, String ServiceName){
        newServiceRequests.add(new ServiceRequest(plane, ServiceName));
    }

    public List<ServiceRequest> getNewServiceRequests(){
        return newServiceRequests;
    }

    public List<ServiceRequest> getServiceRequestsInProgress(){ return serviceRequestsInProgress; }

    @Override
    public void register(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void unregister(Observer obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(obj -> obj.update());
    }

    @Override
    public void notifyObservers(Object obj) {
        observers.forEach(observer -> observer.update(obj));
    }
}
