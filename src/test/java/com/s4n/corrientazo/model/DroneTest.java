package com.s4n.corrientazo.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

public class DroneTest {

    @Test
    public void isValidPositionTest(){
        IDrone drone = Drone.builder().id(1).build();
        Assertions.assertTrue(drone.isValidPosition());
    }

    @Test
    public void isInvalidPositionTest(){
        IDrone drone = Drone.builder().id(1).position(new Point2D.Double(11,10)).build();
        Assertions.assertFalse(drone.isValidPosition());
        drone = Drone.builder().id(1).position(new Point2D.Double(11,12)).build();
        Assertions.assertFalse(drone.isValidPosition());
        drone = Drone.builder().id(1).position(new Point2D.Double(5,-12)).build();
        Assertions.assertFalse(drone.isValidPosition());
        drone = Drone.builder().id(1).position(new Point2D.Double(-15,-2)).build();
        Assertions.assertFalse(drone.isValidPosition());
    }

    @Test
    public void moveTest(){
        IDrone drone = Drone.builder().id(1).build();
        drone.move();
        drone.move();
        Assertions.assertEquals("(0, 2) North direction", drone.report());
    }

    @Test
    public void changeLeftDirectionTest() throws IllegalAccessException {
        IDrone drone = Drone.builder().id(1).build();
        drone.changeDirection('I');
        drone.move();
        Assertions.assertEquals("(-1, 0) West direction", drone.report());
    }

    @Test
    public void changeRightDirectionTest() throws IllegalAccessException {
        IDrone drone = Drone.builder().id(1).build();
        drone.changeDirection('D');
        drone.move();
        Assertions.assertEquals("(1, 0) East direction", drone.report());
    }

    @Test
    public void changeDirectionToSouthTest() throws IllegalAccessException {
        IDrone drone = Drone.builder().id(1).build();
        drone.changeDirection('D');
        drone.changeDirection('D');
        drone.move();
        Assertions.assertEquals("(0, -1) South direction", drone.report());
    }
    @Test
    public void changeDirectionErrorTest() {
        IDrone drone = Drone.builder().id(1).build();
        Assertions.assertThrows(IllegalArgumentException.class, ()-> drone.changeDirection('W'));
    }

}
