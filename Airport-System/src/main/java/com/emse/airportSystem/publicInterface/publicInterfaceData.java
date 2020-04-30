package com.emse.airportSystem.publicInterface;

import com.emse.airportSystem.planeManager.model.Plane;
import com.emse.airportSystem.planeManager.states.InAir;
import com.emse.airportSystem.planeManager.states.State;

import java.util.ArrayList;
import java.util.List;

public class publicInterfaceData {
    public List<Plane> planeList = new ArrayList<>();


 public void mockingData(){
     String model = "Airbus A380";
     State state = null;
     state = InAir::new;
     String planeId;

     for(int i = 0; i<=40; i++){
         planeId = model + i;
         Plane plane = new Plane(model, state, planeId);
         planeList.add(plane);
     }
 }



 public List<Plane> getPlaneList(){
     return planeList;
 }

}
