package com.emse.airportSystem.publicInterface;

import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.service.impl.PlaneManager;
import com.emse.airportSystem.planeManager.states.InAir;
import com.emse.airportSystem.planeManager.states.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class publicInterfaceData {
    @Autowired
    PlaneManager planeManager;


     public void mockingData(){
         String model = "Airbus A380";
         State state = null;
         state = new InAir();
         String planeId;

         for(int i = 0; i<=40; i++){
             planeId = model + i;
             planeManager.addPlane(model, state, planeId);
         }
    }

    public void advancePlaneState(){
         planeManager.proceedToNextState(planeManager.getPlanes().get(1));
    }
}
