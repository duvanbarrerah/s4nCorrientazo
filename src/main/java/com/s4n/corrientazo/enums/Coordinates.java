package com.s4n.corrientazo.enums;


import lombok.Getter;

@Getter
public enum Coordinates {
    N('N', "North"),
    S('S', "South"),
    E('E', "East"),
    W('W', "West");

    private char code;
    private String name;

    Coordinates(char code, String name) {
        this.code = code;
        this.name = name;
    }
}
