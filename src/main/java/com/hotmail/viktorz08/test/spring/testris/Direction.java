package com.hotmail.viktorz08.test.spring.testris;

public enum Direction {
    NONE("none"),

    NORTH("north"),
    SOUTH("south"),
    EAST("east"),
    WEST("west");

    private String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    public static Direction fromDirection(String direction) {
        for (Direction value : values()) {
            if (value.direction.equalsIgnoreCase(direction)) {
                return value;
            }
        }

        return null;
    }
}
