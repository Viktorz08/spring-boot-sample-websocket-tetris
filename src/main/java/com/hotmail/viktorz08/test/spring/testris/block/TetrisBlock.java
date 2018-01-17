package com.hotmail.viktorz08.test.spring.testris.block;

import com.hotmail.viktorz08.test.spring.testris.location.Direction;
import com.hotmail.viktorz08.test.spring.testris.location.Location;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collection;

public interface TetrisBlock {
    void setSession(WebSocketSession session);

    Location getHead();

    Collection<Location> getBlockParts();

    void setDirection(Direction direction);

    String getLocationsJson();

    int getId();

    String getHexColor();
}
