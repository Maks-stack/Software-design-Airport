package com.emse.Airport_System;

import java.util.ArrayList;

public class PlaneControllerTest {

    public static void mainloop() {

        ArrayList<Plane> planes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            String id = "plane" + i;
            String model = "plane" + i;
            if (i % 2 == 0) {
                planes.add(new Plane(id, model));
            } else {
                planes.add(new Plane(id, model, new PlaneStateInAir()));
            }
        }

        for (Plane plane : planes
        ) {
            System.out.printf("ID: %s Model: %s State: %s\n", plane.getId(), plane.getModel(), plane.getState().getStateName());
        }
        System.out.printf("______________________________________________\n");

        planes.get(0).setState(new PlaneStateAwaitingTrackForTakeOff());

        for (Plane plane : planes
        ) {
            System.out.printf("ID: %s Model: %s State: %s\n", plane.getId(), plane.getModel(), plane.getState().getStateName());
        }
        System.out.printf("______________________________________________\n");

        planes.get(0).getState().proceedToNextState(planes.get(0));

        for (Plane plane : planes
        ) {
            System.out.printf("ID: %s Model: %s State: %s\n", plane.getId(), plane.getModel(), plane.getState().getStateName());
        }
        System.out.printf("______________________________________________\n");




    }
}
