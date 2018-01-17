package com.hotmail.viktorz08.test.spring.testris.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Location {
    private static final Logger LOG = LoggerFactory.getLogger(Location.class);


    public int x;
    public int y;
    public static final int GRID_SIZE = 10;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Location getAdjacentLocation(Direction direction) {
        switch (direction) {
            case NORTH:
                return new Location(this.x, this.y - Location.GRID_SIZE);
            case SOUTH:
                return new Location(this.x, this.y + Location.GRID_SIZE);
            case EAST:
                return new Location(this.x + Location.GRID_SIZE, this.y);
            case WEST:
                return new Location(this.x - Location.GRID_SIZE, this.y);
            case NONE:
                // fall through
            default:
                return this;
        }
    }

    public void moveLocation(Direction direction){
        switch (direction) {
            case NORTH:
                this.y -= Location.GRID_SIZE;
                break;
            case SOUTH:
                this.y += Location.GRID_SIZE;
                break;
            case EAST:
                this.x += Location.GRID_SIZE;
                break;
            case WEST:
                this.x -= Location.GRID_SIZE;
                break;
            case NONE:
                // fall through
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Location location = (Location) o;
        return this.x == location.x && this.y == location.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}