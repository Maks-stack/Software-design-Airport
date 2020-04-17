package com.emse.airportSystem.serviceManager.service;

import com.emse.airportSystem.exceptions.ServiceNotAvailableException;
import com.emse.airportSystem.exceptions.RequestNotAvailableException;
import com.emse.airportSystem.observer.Observable;
import com.emse.airportSystem.observer.Observer;
import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.states.InAir;
import com.emse.airportSystem.serviceManager.model.PlaneService;
import com.emse.airportSystem.serviceManager.model.ServiceGate;
import com.emse.airportSystem.serviceManager.model.ServiceRefuel;
import com.emse.airportSystem.serviceManager.model.ServiceRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceManager implements Observable{
    Map<String, Object> services  = new HashMap<>();
    Map<String, ServiceRequest> newServiceRequests = new HashMap<>();
    List<ServiceRequest> serviceRequestsInProgress = new ArrayList<ServiceRequest>();
    List<Observer> observers = new ArrayList<Observer>();

    {
        for (int i = 0; i < 10; i++) {
            String name = "Gate "  + i;
            String id = name.replace(" ","").toLowerCase();
            services.put(id, new ServiceGate(name, id , this));
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

    public void assignService(String requestId, String serviceId) throws ServiceNotAvailableException,RequestNotAvailableException {
        ServiceRequest newServiceInProgress = newServiceRequests.get(requestId);
        if (newServiceInProgress != null){
            serviceRequestsInProgress.add(newServiceInProgress);
            newServiceRequests.remove(newServiceInProgress.getId());
        } else {
            throw new RequestNotAvailableException("Request "+requestId+" is not available");
        }

        PlaneService service = (PlaneService) services.get(serviceId);
        if (service.getAvailable()) {
            Thread t = new Thread(service);
            t.start();
        } else {
            throw new ServiceNotAvailableException("Service " + service.getName() + " is not available");
        }
    }
    
    public void cancelService(String serviceId) {
        PlaneService service = (PlaneService) services.get(serviceId);
        service.cancelService();
    }


    public void assignRandomService() throws ServiceNotAvailableException, RequestNotAvailableException {
        try{
            Plane mockPlane = new Plane("Mock1",new InAir(),"Mock1");
            ServiceRequest serviceRequest = new ServiceRequest(mockPlane,"Gate");
            newServiceRequests.put(serviceRequest.getId(), serviceRequest);
            this.assignService(serviceRequest.getId(), "gate"+new Random().nextInt(10));
        } catch (Exception e) {
            throw e;
        }
    }


    public void registerNewRequest(Plane plane, String ServiceName){
        ServiceRequest serviceRequest = new ServiceRequest(plane, ServiceName);
        newServiceRequests.put(serviceRequest.getId(), serviceRequest);
    }

    public Collection<ServiceRequest> getNewServiceRequests(){
        return newServiceRequests.values();
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
