package com.emse.Airport_System.Service;

import com.emse.Airport_System.Plane.Plane;

import java.util.*;

public class ServiceManager {
    Map<String, Object> services  = new HashMap<>();
    List<ServiceRequest> newServiceRequests = new ArrayList<ServiceRequest>();
    List<ServiceRequest> serviceRequestsInProgress = new ArrayList<ServiceRequest>();
    List<ServiceRequest> serviceRequestsCompleted = new ArrayList<ServiceRequest>();

    private static ServiceManager instance = null;

    private ServiceManager() {
        // Exists only to defeat instantiation.
    }

    public static ServiceManager getInstance() {
        if(instance == null) {
            instance = new ServiceManager();
        }
        return instance;
    }

    public void initializeServices(){
        for (int i = 0; i < 10; i++) {
            String name = "Gate "  + i;
            services.put("gate"+i, new ServiceGate(name));
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

    public void assignService(String serviceId){
        Service sv = (Service) services.get(serviceId);
        sv.setNotAvailable();
    }

    public List<ServiceRequest> getNewServiceRequests(){
        return newServiceRequests;
    }

    public List<ServiceRequest> getServiceRequestsInProgress(){
        return serviceRequestsInProgress;
    }

    public List<ServiceRequest> getServiceRequestsCompleted(){
        return serviceRequestsCompleted;
    }

    public void registerNewRequest(Plane plane, String ServiceName){
        newServiceRequests.add(new ServiceRequest(plane, ServiceName));
    }

}
