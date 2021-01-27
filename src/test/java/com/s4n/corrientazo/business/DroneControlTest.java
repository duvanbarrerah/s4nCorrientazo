package com.s4n.corrientazo.business;

import com.s4n.corrientazo.exceptions.OutOfLimitException;
import com.s4n.corrientazo.model.IDrone;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.anyChar;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class DroneControlTest {

    @Mock
    private IDrone drone;

    private DroneControl droneControl;

    @Test
    public void BuildDroneTest(){
        droneControl = new DroneControl(drone, Collections.emptyList()) ;
        when(drone.getId()).thenReturn(1);
        droneControl.buildDrone(1);
        Assertions.assertEquals(1, droneControl.getDrone().getId());
    }

    @Test
    public void moveDroneTest() throws OutOfLimitException {
        droneControl = new DroneControl(drone, new ArrayList<>()) ;
        doNothing().when(drone).move();
        doNothing().when(drone).changeDirection(anyChar());
        when(drone.isValidPosition()).thenReturn(true);

        List<String> expectedResult = Collections.singletonList("(-2, 4) West direction");
        when(drone.report()).thenReturn("(-2, 4) West direction");

        droneControl.moveDrone(Collections.singletonList("AAAAIAA"));

        Assertions.assertEquals(expectedResult, droneControl.getResultList());
        verify(drone, times(6)).move();
        verify(drone, times(1)).changeDirection(anyChar());
        verify(drone, times(1)).isValidPosition();
        verify(drone, times(1)).report();
    }

    @Test
    public void moveDroneExceptionTest() {
        droneControl = new DroneControl(drone, new ArrayList<>()) ;
        doNothing().when(drone).move();
        doNothing().when(drone).changeDirection(anyChar());
        when(drone.isValidPosition()).thenReturn(false);

        Assertions.assertThrows(OutOfLimitException.class, () -> droneControl.moveDrone(Collections.singletonList("AAAAIAA")));
        verify(drone, times(6)).move();
        verify(drone, times(1)).changeDirection(anyChar());
        verify(drone, times(1)).isValidPosition();
    }
}
