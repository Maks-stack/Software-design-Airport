package com.emse.Airport_System;

import java.util.ArrayList;

public class PlaneManager {
    public static void mainloop() {

        ArrayList<Plane> planes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String id = "plane" + i;
            String model = "plane" + i;

            if (i % 2 == 0) {
                Plane plane = new Plane(id, model);
                planes.add(plane);
                plane.register(new PlaneObserver());

            } else {
                Plane plane = new Plane(id, model, new PlaneStateInAir());
                planes.add(plane);
                plane.register(new PlaneObserver());
            }
        }

        planes.get(0).setState(new PlaneStateAwaitingTrackForTakeoff());
        printPlanes(planes);
        planes.get(0).getState().proceedToNextState(planes.get(0));
        printPlanes(planes);
        planes.get(1).setState(new PlaneStateServicing());
        printPlanes(planes);

        ServiceManager SM = ServiceManager.getInstance();
        SM.initializeServices();
        SM.printAllServices();


    }


    private static void printPlanes(ArrayList<Plane> planes){
        for (Plane plane : planes
        ) {
            System.out.printf("ID: %s Model: %s State: %s\n", plane.getId(), plane.getModel(), plane.getState().getStateName());
            if (plane.getState().getStateName().equalsIgnoreCase("Servicing")){
                System.out.println("Assigned services:");
            } else {
                System.out.println("        No services can be assigned in this state");
            }

        }
        System.out.printf("______________________________________________\n");

    }

}


