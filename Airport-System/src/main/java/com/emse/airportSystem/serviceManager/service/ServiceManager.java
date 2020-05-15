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
        try {
            ServiceRequest originalServiceRequest = newServiceRequests.get(requestId);
            service.assignRequest(originalServiceRequest);
            registerServiceRequestsInProgress(requestId);
            planeManager.handleServiceAssigned(originalServiceRequest.getPlane(), service);
        } catch (Exception e) {
            throw new RequestNotFoundException("Request " +requestId+ " was not found");
        }
    }

    public void cancelService(String serviceId) {
        PlaneService service = (PlaneService) services.get(serviceId);
        Plane plane = service.getAssignedPlane();
        service.cancelService();
        planeManager.handleServiceCanceled(plane,service);
    }

    public void assignRandomService() throws ServiceNotAvailableException, RequestNotFoundException {
        try{
            Plane mockPlane = new Plane("Mock1",new InAir(),"Mock1", planeManager);
            planeManager.addPlane(mockPlane);
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

    public void notifyServiceCompleted(PlaneService service, String planeId) {
        planeManager.handleServiceCompleted(planeId,service);
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
