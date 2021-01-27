package com.s4n.corrientazo.model;

public interface IDrone {
    void move();
    void changeDirection(char direction) throws IllegalArgumentException;
    boolean isValidPosition();
    String report();
    int getId();
}
