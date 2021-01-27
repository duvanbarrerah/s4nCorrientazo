package com.s4n.corrientazo.business;

import com.s4n.corrientazo.util.Constants;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Corrientazo {

    public void runDronesLogic(){
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(Constants.DRONES_NUMBER);
        for (int i=1; i<= Constants.DRONES_NUMBER; i++){
            DroneControl droneControl = new DroneControl();
            droneControl.buildDrone(i);
            scheduledExecutorService.submit(droneControl);
        }
        scheduledExecutorService.shutdown();
    }
}
